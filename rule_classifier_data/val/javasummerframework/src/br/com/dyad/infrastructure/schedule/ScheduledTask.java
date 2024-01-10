package br.com.dyad.infrastructure.schedule;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.dyad.infrastructure.annotations.MetaField;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.User;


@Entity
@Table(name="SYSSCHEDULER")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="classId", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="-89999999999930")
/**
 * 		ScheduledTask task = new ScheduledTask();
 *		task.createId(this.getDatabase());
 *		task.setName("Execucao remota da validacao " + validacao.getId() );
 *		task.setType( ScheduleType.Once.getCode() );
 *		task.setJob( ValidacoesDaBaseScheduler.class.getName() );
 *		task.setUser( usuarioLogado );
 *		task.setParameters("validacaoId=" + validacao.getId() + ";userId=" + usuarioLogado.getId() );
 *		SystemScheduler.scheduleJob(task, this.getDatabase());		
 */
public class ScheduledTask extends BaseEntity{
	
	@Column(length=100, nullable=true)
	private String name;
	@Column(nullable=true)
	private Integer type;
	@Column(nullable=true)
	@Temporal(TemporalType.TIME)
	private Date time;
	@Column(nullable=true)
	@Temporal(TemporalType.DATE)
	private Date date;	
	@Column(nullable=true)
	private Integer dayOfMonth;
	@Column(nullable=true)
	private Integer dayOfWeek;
	@Column(length=500, nullable=true)
	private String job;
	@Column(length=1000, nullable=true)
	private String parameters;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = true)
	private User user;	
	
	public String getName() {
		return name;
	}
	public void setName(String nome) {
		this.name = nome;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getJob() {
		return job;
	}
	/**
	 * @param jog Recebe o nome da classe que será executada. Esta classe deve estender a classe DyadJob.
	 * @see DyadJob
	 * */	
	public void setJob(String job) {
		this.job = job;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public void defineFields() {
		super.defineFields();							
		
		this.defineField(
				"type", 
				MetaField.order, 100,
				MetaField.required, true,
				MetaField.options, ScheduledTask.typeOptions
		);
		
		this.defineField(
				"name", 
				MetaField.order, 200,
				MetaField.required, true
		);
		
		this.defineField(
				"time", 
				MetaField.order, 300,
				MetaField.required, false
		);
		
		this.defineField(
				"date", 
				MetaField.order, 400,
				MetaField.required, false
		);
		
		this.defineField(
				"dayOfMonth", 
				MetaField.order, 500,
				MetaField.required, false
		);
		
		this.defineField(
				"dayOfWeek", 
				MetaField.order, 600,
				MetaField.required, false
		);
		
		this.defineField(
				"job", 
				MetaField.order, 700,
				MetaField.required, false
		);
		
		this.defineField(
				"parameters", 
				MetaField.order, 800,
				MetaField.required, false
		);
		
		this.defineField(
				"user", 
				MetaField.order, 900,
				MetaField.required, true,
				MetaField.beanName, User.class.getName()
				
				
		);
		
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@SuppressWarnings("unchecked")
	public static final transient ArrayList typeOptions = BaseEntity.getOptions(ScheduleType.class);
	
	//public static final transient ArrayList typeOptions = BaseEntity.getOptions(ScheduleType.Interval, "Interval", 
	//		ScheduleType.Once, "Once", ScheduleType.Daily, "Daily", ScheduleType.Monthly, "Monthly", ScheduleType.Weekly, "Weekly");

}
