package prince.admin.quartz;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.google.gson.Gson;



/**
 * Servlet implementation class QuartzServlet
 */
@WebServlet("/Quartz/list")
public class QuartzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuartzServlet() {
        super();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<QuartzGroup> listQuartzGroup = getQuartzJobs(request);
			//JSONObject jsonObject = new JSONObject();
			Gson gson = new Gson();
			//JSONObject<String,Object> obj=new JSONObject<String,Object>();
			
			//jsonObject.put("quartzJob", listQuartzGroup);
			String json = gson.toJson(listQuartzGroup);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	private List<QuartzGroup> getQuartzJobs(HttpServletRequest request) throws SchedulerException, IOException{
		Scheduler scheduler = schedulerFactory.getScheduler();
		
		List<QuartzGroup> listQuartzGroup = new ArrayList<QuartzGroup>();
		for(String groupName : scheduler.getJobGroupNames()){
			QuartzGroup group = new QuartzGroup();
			System.out.println("===========================");
			System.out.println("groupName: " + groupName);
			group.setGroupName(groupName);
			
			List<QuartzJob> quartzJobs = new ArrayList<QuartzJob>();
//			for(String jobName : scheduler.getJobNames(groupName)){
//				System.out.println("jobName: " + jobName);
//				JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
//				
//				QuartzJob job = new QuartzJob();
//				job.setJobName(jobName);
//				
//				//job.setJobDetail(jobDetail);
//				quartzJobs.add(job );
//			}
			
			for(String triggerName : scheduler.getTriggerNames(groupName)){
				System.out.println("triggerName: " + triggerName);
				Trigger trigger = scheduler.getTrigger(triggerName, groupName);
				
				QuartzJob job = new QuartzJob();
				job.setTriggerName(triggerName);
				job.setNextFireTime(trigger.getNextFireTime() );
				job.setPreviousFireTime(trigger.getPreviousFireTime() );
				
				job.setPriority(trigger.getPriority());
				
				trigger.getJobName();
				job.setRunOnceURL(request.getContextPath() + "/Quartz/fire?jobName=" + trigger.getJobName());
				//job.setTrigger(trigger);
				//job.setJobDetail(jobDetail);
				quartzJobs.add(job );
			}
			
			group.setQuartzJobs(quartzJobs);
			listQuartzGroup.add(group);
		}
		
		return listQuartzGroup;
		
		
		
//		System.out.println("groups: " + scheduler.getJobGroupNames()[0]);
//		System.out.println("job name: " + scheduler.getJobNames("DEFAULT")[0]);
//		
//		System.out.println("TriggerGroupNames: " + scheduler.getTriggerGroupNames()[0]) ;
//		System.out.println("trigger names: " + scheduler.getTriggerNames("DEFAULT")[0] );
//		
//		
//		JobDetail jd = scheduler.getJobDetail("runMeJob", "DEFAULT");
//		System.out.println(jd);
//		
//		scheduler.getTrigger("cronTrigger", "DEFAULT").getJobName();
	}
	
	class QuartzGroup implements Serializable{
		private static final long serialVersionUID = 8043297927174939355L;
		
		private String groupName;
		private List<QuartzJob> quartzJobs;
		public String getGroupName() {
			return groupName;
		}
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		public List<QuartzJob> getQuartzJobs() {
			return quartzJobs;
		}
		public void setQuartzJobs(List<QuartzJob> quartzJobs) {
			this.quartzJobs = quartzJobs;
		}
		@Override
		public String toString() {
			return "QuartzGroup [groupName=" + groupName + ", quartzJobs="
					+ quartzJobs + "]";
		}
	}
	
	class QuartzJob implements Serializable{
		private static final long serialVersionUID = -5947319051607794745L;
		private String triggerName;
		//private JobDetail jobDetail;
		private Date nextFireTime;
		private Date previousFireTime;
		//private Trigger trigger;
		private int priority;
		private String runOnceURL;
		
		public Date getNextFireTime() {
			return nextFireTime;
		}
		public void setNextFireTime(Date nextFireTime) {
			this.nextFireTime = nextFireTime;
		}
		public Date getPreviousFireTime() {
			return previousFireTime;
		}
		public void setPreviousFireTime(Date previousFireTime) {
			this.previousFireTime = previousFireTime;
		}
		public String getTriggerName() {
			return triggerName;
		}
		public void setTriggerName(String triggerName) {
			this.triggerName = triggerName;
		}
//		public Trigger getTrigger() {
//			return trigger;
//		}
//		public void setTrigger(Trigger trigger) {
//			this.trigger = trigger;
//		}
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
		}
		public String getRunOnceURL() {
			return runOnceURL;
		}
		public void setRunOnceURL(String runOnceURL) {
			this.runOnceURL = runOnceURL;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
