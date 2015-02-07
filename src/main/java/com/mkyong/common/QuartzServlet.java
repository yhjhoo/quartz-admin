package com.mkyong.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			voidListAllJobs();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	private void voidListAllJobs() throws SchedulerException{
		Scheduler scheduler = schedulerFactory.getScheduler();
		for(String group : scheduler.getJobGroupNames()){
			System.out.println("===========================");
			System.out.println("groupName: " + group);
			
			for(String jobName : scheduler.getJobNames(group)){
				System.out.println("jobName: " + jobName);
			}
		}
		
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
