
NUM_ITERS = 1
SMALL = 1 == 1

if SMALL:
	BASE_NUM_ROWS = 4000
	NUM_BATCHES = 10
	CHECK_RESULTS = True
	ONLY_SORT = False
	TESTS_PER_GROUP = 100
else:
	BASE_NUM_ROWS = 10000 # makes 100 rows of 100 items each!
	NUM_BATCHES = 20
	CHECK_RESULTS = False
	ONLY_SORT = False
	TESTS_PER_GROUP = 100

WIDTH_RATIO = 1

from dovetaildb.dbrepository import MemoryDbRepository
from dovetaildb.servlet import DovetaildbServlet
from dovetaildb.dbrepository import ParsedRequest
from dovetaildb.util import Util
from dovetaildb.StdLib import genUUID
import java.lang.System as System

import pprint
import random
import sys
import time

server_code = '''# DDB_language=jython:2.5.2

from dovetaildb.apiservice import ApiService

def batch_update(api, bag, puts, delete_ids):
	for id in delete_ids:
		api.delete(bag, id)
	for entry in puts:
		api.put(bag, entry)

class BackupApiWrapper(ApiService):
	def __init__(self, inner_api):
		self.inner_api = inner_api
	def query(self, bag, query, options):
		return self.inner_api.query(bag, query, options)
	def put(self, bag, entry):
		return self.inner_api.put(bag, entry)
	def remove(self, bag, id):
		return self.inner_api.remove(bag, id)
	def commit(self):
		return self.inner_api.commit()
	def rollback(self):
		return self.inner_api.rollback()

ALLOWED_METHODS = ['get_public_info']

def request_is_ok(req):
	if req.getParameter('accesskey') == 'iMnwBsL8YjeK41hxVp3W5OnS':
		return True
	if req.getAction() == 'call' and req.getFunctionName() in ALLOWED_METHODS:
		return True
	return False

def wrapApiService(api, request):
	if request_is_ok(request):
		return BackupApiWrapper(api)
	else:
		return None

'''

_vowels = ['a','e','i','o','u']
_cons = [chr(ch) for ch in range(ord('a'),ord('z')) if chr(ch) not in _vowels]
def add_word(r, buffer):
	word=[]
	word.append(r.choice(_cons).upper())
	for _ in range(r.choice([1,1,2,2,2,3,3,4])):
		word.append(r.choice(_vowels))
		word.append(r.choice(_cons))
	buffer.append(''.join(word))

def init():
	repo = MemoryDbRepository()
	repo.initMeta()
	servlet = DovetaildbServlet()
	servlet.setRepo(repo)
	ret = servlet.handle(ParsedRequest.makeExecute("_meta", "repo.setDb(\"m_db\",Packages.dovetaildb.StdLib.makeFsDb(\""+bagdir+"\",false,"+str(WIDTH_RATIO)+"))"))
	#ret = servlet.handle(ParsedRequest.makeExecute("_meta", "repo.setDb(\"m_db\",Packages.dovetaildb.StdLib.makeMemoryDb())"))
	code_to_put_code = 'repo.setCodeFile("m_db","server.py",\n\n\n%s)' % Util.jsonEncode(server_code)
	ret = servlet.handle(ParsedRequest.makeExecute('_meta', code_to_put_code))
	def mput(bag, inserts, del_ids):
		req = ParsedRequest.makeCall('m_db', 'batch_update', Util.jsonEncode([bag,inserts,[]]))
		req.setParameter('accesskey', 'iMnwBsL8YjeK41hxVp3W5OnS')
		t0 = time.time()
		ret = servlet.handle(req)
		t1 = time.time()
		req = ParsedRequest.makeExecute('m_db', 'api.getMetrics(9)')
		req.setParameter('accesskey', 'iMnwBsL8YjeK41hxVp3W5OnS')
		print Util.jsonEncode(servlet.handle(req))
		return ret, t1-t0
	def mquery(bag, constraints, opts):
		req = ParsedRequest.makeQuery('m_db', bag, Util.jsonEncode(constraints))
		req.setOptions(Util.jsonEncode(opts))
		t0 = time.time()
		ret = []
		itr = servlet.handle(req)
		while itr.hasNext():
			ret.append(Util.jsonDecode(Util.jsonEncode(itr.next())))
		return ret, time.time()-t0
	def mcall(mth, *args):
		return servlet.handle(ParsedRequest.makeCall('m_bag', mth, args))
	return mput, mquery
	

def run_basic_test():
	pass
	
def run_load_test():
	mput,mquery = init()
	rand = random.Random(456)

	num_iters = NUM_ITERS

	base_num_rows = BASE_NUM_ROWS
	BATCH_SIZE = max(1, base_num_rows / NUM_BATCHES)
	
	colors = sorted(["red", "green", "blue", "pruple", 
	          "yellow", "orange", "teal", "magenta", "brown", "black"])
	phases = [1,2,3,4]
	all_stats = []
	rangePoints = [rand.uniform(0,1) for _ in xrange(500)]
	rows = []
	for iter in range(num_iters):
		num_rows = base_num_rows * (10 ** iter)
		stats = {'num_rows': num_rows}
		totsz=0
		curid=0
		tm = 0
		while curid < num_rows:
			batch = []
			for row in xrange(min(num_rows, BATCH_SIZE)):
				text = []
				while len(' '.join(text)) < 64:
					add_word(rand, text)
				entry = {'id': '%08d' % curid, 
					'color':rand.choice(colors), 
					'phase':rand.choice(phases), 
					'points':rand.uniform(0,1), 
					'description': ' '.join(text)}
				sentry = str(entry)
				totsz += len(sentry)
				batch.append(entry)
				curid += 1
			tm += mput('peer', batch, [])[1]
			if CHECK_RESULTS:
				rows.extend(batch)
		stats['insert_time'] = tm
		stats['source_size'] = totsz
		all_stats.append(stats)
		time.sleep(tm*2+5)
		test(TESTS_PER_GROUP, mquery, colors, rangePoints, rand, all_stats, rows, num_rows)
	print ' --------- '
	print pprint.pformat(sorted(all_stats,key=lambda x:x.get('mean')))
	#print sum([s['insert_time'] for s in all_stats])
	#sys.stdin.readline()
	
	#stats_string = ["<tr><td>"+r['num_rows']+"</td><td>"+cstr1+"</td><td>"+cstr2+"</td><td>"+(timeTotal/NUM_ITRS)+"</td></tr>");
	#					System.out.println("num objects:"+numRows+"  color:"+cstr1+"  score:"+cstr2+"  ms:"+(timeTotal/NUM_ITRS));
	
	'''
<?xml version="1.0" encoding="UTF-8"?>
<page name=\"performance\">
<title>DovetailDB - Performance</title>
<header_image>img/header04.png</header_image>
<section class="sunken main" head="Query Performance" subhead="">
<p>To generate performance data, we insert randomly generated objects into a bag and then query for them using various methods.
Each object contains: 
(1) a "color" field randomly selected from 10 color names, 
(2) a "phase" field selected from the integers 1-4,
(3) a "points" field randomly generated by a double precision number between 0.0 and 1.0, and 
(4) a "description" field composed of at least 64 bytes of random text.</p>
<p>For each combination of query parameters, we run %(num_iters)s iterations.  On each iteration, we randomly select the constraint values and a limit of either 5, 10, or 20.
"range" constraints are selected with a min and a max randomly selected between 0.0 and 1.0. "in" constraints are selected with 2-5 random values.</p>
<table><tr><th>number of objects in bag</th><th>'color' constraint</th><th>'points' constraint</th><th>mean duration(ms)</th></tr>

</table>
<section class=\"sunken main\" head=\"Update Performance\" subhead=\"\">
<p>On disk, these objects consume roughly twice their size when encoded in JSON.
</section></page>

'''

def genPc(cstrs, field, desc, vals, r, filterfns):
	if desc == "none" or desc == "sort":
		return;
	if desc == "is":
		val = r.choice(vals)
		cstrs[field] = val
		filterfns.append(lambda e:e[field]==val)
	elif desc == "in":
		vals = [r.choice(vals) for _ in xrange(r.choice([2,3,4,5]))]
		cstrs[field] = ['|', vals]
		filterfns.append(lambda e:e[field] in vals)
	elif desc == "range":
		min, max = sorted([r.choice(vals),r.choice(vals)])
		cstrs[field] = ['[]', min, max]
		def ranger(e):
			return min <= e[field] <= max
		filterfns.append(ranger)

def test(num_iters, mquery, colors, rangePoints, r, all_stats, rows, num_rows):
	if ONLY_SORT:
		cstr1s = ["none"]
		cstr2s = ["sort"]
	else:
		cstr1s = ["none","is","in","range"]
		cstr2s = ["none","range","sort"]
	for cstr2 in cstr2s:
		for cstr1 in cstr1s:
			if (cstr1=="sort" and cstr2=="sort"):
				continue
			timeTotal = 0
			for itr_idx in xrange(num_iters * 2):
				filterfns = []
				orderfn = None
				constraints = {}
				genPc(constraints, "color", cstr1, colors, r, filterfns)
				genPc(constraints, "points", cstr2, rangePoints, r, filterfns)
				opts = {'limit': r.choice([5,10,20])}
				if cstr1 == 'sort':
					points = [[color,r.uniform(0.1,0.9)] for color in colors]
					opts['score'] = {'color':['LookupTableScore',points]}
				if cstr2 == 'sort':
					opts['score'] = {'points':['NumericScore']}
					orderfn = lambda e:e['points']
				print constraints, opts
				ret, tm = mquery('peer', constraints, opts)
				
				print ret, tm
				if itr_idx >= num_iters:
					timeTotal += tm
				
				if not CHECK_RESULTS:
					continue
				matching = [row for row in rows if len([f for f in filterfns if not f(row)])==0]
				if orderfn:
					matching = sorted(matching, key=orderfn, reverse=True)[:opts['limit']]
					print 'ret      ',ret
					print 'matching ',matching[:200]
					ret, matching = map(lambda l:[str(e['id']) for e in l], [ret,matching])
					ok = ret == matching
				else:
					ret, matching = map(lambda l:[str(e['id']) for e in l], [ret,matching])
					ok = len(set(ret).difference(matching)) == 0
				print 'ret      ',ret
				print 'matching ',matching[:200]
				print 'match?   ',ok
				if not ok:
					print ''
					print ''
					time.sleep(1)
					sys.exit(1)
					
			all_stats.append({'color':cstr1,'points':cstr2,'num_rows':num_rows,'mean':timeTotal/float(num_iters)})


if testtype == 'load':
	run_load_test()
elif testtype == 'basic':
	run_basic_test()
else:
	raise Exception()
