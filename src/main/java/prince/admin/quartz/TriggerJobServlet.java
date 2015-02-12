package prince.admin.quartz;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet implementation class TriggerJob
 */
@WebServlet("/Quartz/fire")
public class TriggerJobServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8675860126185803115L;

	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TriggerJobServlet() {
        super();
        // TODO Auto-generated constructor stub
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Scheduler scheduler = schedulerFactory.getScheduler();
		String jobName = request.getParameter("jobName");
		try {
			
			if(scheduler.isStarted()){
				log.info("Scheduler is running");
				
				scheduler.triggerJob(jobName, "DEFAULT");
			}else{//if scheduler is not started, created one
				JobDetail jd = scheduler.getJobDetail(jobName, "DEFAULT");
				
				StdSchedulerFactory sf = new StdSchedulerFactory();
				Scheduler tmpScheduler = sf.getScheduler();
				SimpleTrigger simpleTrigger = new SimpleTrigger(jobName);
				simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), 5));
				
				tmpScheduler.scheduleJob(jd, simpleTrigger);
				tmpScheduler.triggerJob(jobName, "DEFAULT");
				//sf.initialize();
				tmpScheduler.start();
				tmpScheduler.shutdown(true);
			}
			
			/*String jobName = request.getParameter("jobName");
			if(jobName==null || jobName.trim().equals("")){
				jobName = "runMeJob";
			}
			
			
			scheduler.triggerJob(jobName, "DEFAULT");*/
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
