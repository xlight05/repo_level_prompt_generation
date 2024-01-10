package se.openprocesslogger.proxy.dojo;

import java.util.ArrayList;
import java.util.Arrays;

/****
 * 
 * Builds trees from string-arrays. 
 * Is intended to be used with jabsorb and dojo:
 * 
 * var myTreeData = jsonrpc.myTreeBuildingObject.buildTreeWrapper(); // calls getTree with appropriate parameters
 * var myStore = new dojo.data.ItemFileWriteStore(myTreeData);
 * var myTree = new dijit.Tree({ store: myStore, id: "myTree"}, document.createElement("div"));
 * 
 * Example on tree structure:
 * 
 * Machine X/Station 1/Plc variable A
 * Machine X/Station 1/Plc variable B
 * Machine X/Station 1/Plc variable C
 * Machine X/Station 2/Plc variable A
 * Machine X/Station 2/Sub station P/Plc variable S
 * 
 * Will show up as:
 * 
 *  Machine X
 *    |- Station 1
 *    |     |- Plc variable A
 *    |     |- Plc variable B
 *    |     |- Plc variable C
 *    |
 *    |- Station 2
 *          |- Plc variable A
 *          |- Sub station P
 *                 |- Plc variable S
 *
 *
 * @author Maja Arvehammar (maja.arvehammar@ipbyran.se)
 *
 */
public class TreeBuilderUtil {
	private static final String SEPARATOR = "/";
	
	String id;
	String name;
	ArrayList<TreeBuilderUtil> items;
	public TreeBuilderUtil(String id){
		items = new ArrayList<TreeBuilderUtil>(); 
		this.id = id;
		String[] names = id.split(SEPARATOR);
		name = names[names.length-1];
	}
	
	public static TreeHolder getTree(String[] varNames){
		Arrays.sort(varNames);
		
		DojoTree tree = new DojoTree();
		tree.setLabel("name");
		tree.setIdentifier("id");
		if(varNames.length == 0 || varNames == null) return new TreeHolder(tree);
		ArrayList<TreeBuilderUtil> tops = new ArrayList<TreeBuilderUtil>();
		
		for(String fullVarName : varNames){
			String topName = fullVarName.split(SEPARATOR)[0];
			TreeBuilderUtil topNode = null;
			for(TreeBuilderUtil top : tops){ // find out where to append name
				if(top.name.equals(topName)){
					topNode = top;
				}
			}
			if(topNode==null){ // No top node yet
				topNode = new TreeBuilderUtil(topName);
				tops.add(topNode);
			}
			append(topNode, fullVarName);
		}
		
		// Build tree		
		DojoTreeItem[] topItem = new DojoTreeItem[tops.size()];
		int index = 0;
		for(TreeBuilderUtil top : tops){
			topItem[index++] = getItem(top);
		}
		tree.setItems(topItem);
		return new TreeHolder(tree);
	}
	
	public static void append(TreeBuilderUtil top, String varName){
		if(varName.matches(top.id+SEPARATOR+".*")){
			for(TreeBuilderUtil item : top.items){ 
				if(varName.matches(item.id+SEPARATOR+".*")){ // If match with a child, try add to a child
					append(item, varName);
					return;
				}
			}
			// Add to last of items. Create a new subtree with 1 child for each level
			top.items.add(getBranch(null,top.id+SEPARATOR, varName.replaceFirst(top.id+SEPARATOR, "")));
		}else{
			System.out.println("#### SERIOUS ERRROR ####");
		}
	}
	
	/***
	 * Builds tree from a/b/c with 
	 * 
	 * @param prefix possible subtree prefix
	 * @param subTreeString
	 * @return Tree
	 */
	public static TreeBuilderUtil getBranch(TreeBuilderUtil child, String prefix, String subTreeString){
		TreeBuilderUtil node = new TreeBuilderUtil(prefix+subTreeString);
		if(child!=null) node.items.add(child);
		if(subTreeString.contains(SEPARATOR)){ // Has parent
			String parentName = subTreeString.substring(0, subTreeString.lastIndexOf(SEPARATOR));
			if(!parentName.equals("")){
				return getBranch(node, prefix,parentName);
			}
		}
		return node;
	}
		
	public static DojoTreeItem getItem(TreeBuilderUtil top){
		if(top.items.size() > 0){ // subtree
			DojoSubTree tree = new DojoSubTree(top.name, top.id);
			DojoTreeItem[] children = new DojoTreeItem[top.items.size()];
			for(int i=0; i<top.items.size(); i++){
				children[i] = getItem(top.items.get(i));
			}
			tree.setChildren(children);
			return tree;
		}else{ // leaf
			return new DojoTreeItem(top.name, top.id);
		}
	}
	
	
}


