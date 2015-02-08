package prince.admin.quartz;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet implementation class TriggerJob
 */
@WebServlet("/Quartz/fire")
public class TriggerJob extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TriggerJob() {
        super();
        // TODO Auto-generated constructor stub
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Scheduler scheduler = schedulerFactory.getScheduler();
		try {
			String jobName = request.getParameter("jobName");
			if(jobName==null || jobName.trim().equals("")){
				jobName = "runMeJob";
			}
			
			scheduler.triggerJob(jobName, "DEFAULT");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
