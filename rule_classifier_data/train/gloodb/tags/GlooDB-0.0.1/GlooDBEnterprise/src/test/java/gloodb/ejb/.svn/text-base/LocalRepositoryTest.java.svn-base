package gloodb.ejb;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import gloodb.Repository;
import gloodb.RepositoryInterceptor;
import gloodb.utils.RepositorySysoutInterceptor;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ejb.ScheduleExpression;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerHandle;
import javax.ejb.TimerService;

import org.junit.Before;
import org.junit.Test;

public class LocalRepositoryTest {
    private static final String namespace = "target/LocalRepositoryTest";
    private LocalRepositoryBean localRepository;
    
    @Before
    public void setUp() throws Exception {
        localRepository = new LocalRepositoryBean();
        localRepository.timerService = new TimerService() {
            private final CopyOnWriteArrayList<Timer> timers = new CopyOnWriteArrayList<Timer>();
            
            @Override
            public Timer createCalendarTimer(ScheduleExpression arg0) {
                return null;
            }

            @Override
            public Timer createCalendarTimer(ScheduleExpression arg0, TimerConfig arg1)  {
                return null;
            }

            @Override
            public Timer createIntervalTimer(long arg0, long arg1, TimerConfig arg2) {
                return null;
            }

            @Override
            public Timer createIntervalTimer(Date arg0, long arg1, TimerConfig arg2) {
                return null;
            }

            @Override
            public Timer createSingleActionTimer(long arg0, TimerConfig arg1) {
                return null;
            }

            @Override
            public Timer createSingleActionTimer(Date arg0, TimerConfig arg1) {return null;
            }

            @Override
            public Timer createTimer(long arg0, Serializable arg1) {
                return null;
            }

            @Override
            public Timer createTimer(Date arg0, Serializable arg1) {
                return null;
            }

            @Override
            public Timer createTimer(long arg0, long arg1, final Serializable arg2) {
                Timer result = new Timer() {

                    @Override
                    public void cancel() {
                        for(int i = 0; i < timers.size(); i++) {
                            if(timers.get(i).getInfo().equals(arg2)) {
                              timers.remove(i); 
                              return;
                            }
                        }
                    }

                    @Override
                    public TimerHandle getHandle() {
                        return null;
                    }

                    @Override
                    public Serializable getInfo() {
                        return arg2;
                    }

                    @Override
                    public Date getNextTimeout() {
                        return null;
                    }

                    @Override
                    public ScheduleExpression getSchedule() {
                        return null;
                    }

                    @Override
                    public long getTimeRemaining()  {
                        return 0;
                    }

                    @Override
                    public boolean isCalendarTimer() {
                        return false;
                    }

                    @Override
                    public boolean isPersistent() {
                        return false;
                    }};
                    timers.add(result);
                    return result;

            }

            @Override
            public Timer createTimer(Date arg0, long arg1, Serializable arg2) {
                return null;
            }

            @Override
            public Collection<Timer> getTimers() {
                return timers;
            }};
    }

    @Test
    public void testInterceptRepository() {
        assertThat(localRepository.getRepository(namespace), is(notNullValue()));
        assertThat(localRepository.getRepository(namespace + 2), is(notNullValue()));
        assertThat(localRepository.repositoryMap.containsKey(namespace), is(true));
        assertThat(localRepository.timerService.getTimers().size(), is(2));

        Repository repository = localRepository.interceptRepository(namespace, new RepositorySysoutInterceptor());
        assertThat(repository, is(instanceOf(RepositorySysoutInterceptor.class)));
        assertThat(((RepositoryInterceptor)repository).getIntercepted(), is(notNullValue()));
    }

    @Test
    public void testGetRepositoryInstance() {
        assertThat(localRepository.getRepositoryInstance(namespace), is(notNullValue()));
        assertThat(localRepository.repositoryMap.containsKey(namespace), is(true));
    }

    @Test
    public void testGetRepository() {
        assertThat(localRepository.getRepository(namespace), is(notNullValue()));
        assertThat(localRepository.repositoryMap.containsKey(namespace), is(true));
        assertThat(localRepository.timerService.getTimers().size(), is(1));
    }
    
    @Test
    public void testTearDown() {
        assertThat(localRepository.getRepository(namespace), is(notNullValue()));
        assertThat(localRepository.repositoryMap.containsKey(namespace), is(true));
        assertThat(localRepository.timerService.getTimers().size(), is(1));
        localRepository.tearDown();
        assertThat(localRepository.timerService.getTimers().size(), is(0));
    }
    
    @Test
    public void testSetCachePurge() {
        assertThat(localRepository.getRepository(namespace), is(notNullValue()));
        assertThat(localRepository.repositoryMap.containsKey(namespace), is(true));
        assertThat(localRepository.timerService.getTimers().size(), is(1));
        
        for(Timer t: localRepository.timerService.getTimers()) {
            RepositoryInfo info = (RepositoryInfo) t.getInfo();
            assertThat(info.getCachePurgeInterval(), is(60000l));
            assertThat(info.getCachePurgePercentage(), is(10));
            assertThat(info.getNameSpace(), is(namespace));
        }
        
        assertThat(localRepository.setCachePurge("bla", 120000l, 20), is(false));
        assertThat(localRepository.setCachePurge(namespace, 120000l, 20), is(true));
        assertThat(localRepository.timerService.getTimers().size(), is(1));
        
        for(Timer t: localRepository.timerService.getTimers()) {
            RepositoryInfo info = (RepositoryInfo) t.getInfo();
            assertThat(info.getCachePurgeInterval(), is(120000l));
            assertThat(info.getCachePurgePercentage(), is(20));
            assertThat(info.getNameSpace(), is(namespace));
        }
    }
}
