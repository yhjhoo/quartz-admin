package prince.test.admin.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:Spring-*.xml"
		})
public class AppTest{
//	@Autowired
//	private FactoryBean schedulerFactory;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	
	@Test
	public void testScheduler() throws SchedulerException{
		System.out.println("test");
		
		Scheduler scheduler = schedulerFactory.getScheduler();
		System.out.println("groups: " + scheduler.getJobGroupNames()[0]);
		System.out.println("job name: " + scheduler.getJobNames("DEFAULT")[0]);
		
		System.out.println("TriggerGroupNames: " + scheduler.getTriggerGroupNames()[0]) ;
		System.out.println("trigger names: " + scheduler.getTriggerNames("DEFAULT")[0] );
		
		
		JobDetail jd = scheduler.getJobDetail("runMeJob", "DEFAULT");
		System.out.println(jd);
		
		scheduler.getTrigger("cronTrigger", "DEFAULT").getJobName();
		
		
		scheduler.triggerJob("runMeJob", "DEFAULT");
	}
}
