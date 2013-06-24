/*
 * Copyright 2002-2013 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.scoopgmbh.copper.monitoring.client.doc.view.fixture;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.scoopgmbh.copper.monitoring.core.CopperMonitoringService;
import de.scoopgmbh.copper.monitoring.core.model.AdapterHistoryInfo;
import de.scoopgmbh.copper.monitoring.core.model.AuditTrailInfo;
import de.scoopgmbh.copper.monitoring.core.model.CopperInterfaceSettings;
import de.scoopgmbh.copper.monitoring.core.model.DependencyInjectorInfo;
import de.scoopgmbh.copper.monitoring.core.model.DependencyInjectorInfo.DependencyInjectorTyp;
import de.scoopgmbh.copper.monitoring.core.model.LogData;
import de.scoopgmbh.copper.monitoring.core.model.LogEvent;
import de.scoopgmbh.copper.monitoring.core.model.MeasurePointData;
import de.scoopgmbh.copper.monitoring.core.model.MessageInfo;
import de.scoopgmbh.copper.monitoring.core.model.ProcessingEngineInfo;
import de.scoopgmbh.copper.monitoring.core.model.ProcessingEngineInfo.EngineTyp;
import de.scoopgmbh.copper.monitoring.core.model.ProcessorPoolInfo;
import de.scoopgmbh.copper.monitoring.core.model.ProcessorPoolInfo.ProcessorPoolTyp;
import de.scoopgmbh.copper.monitoring.core.model.StorageInfo;
import de.scoopgmbh.copper.monitoring.core.model.SystemResourcesInfo;
import de.scoopgmbh.copper.monitoring.core.model.WorkflowClassMetaData;
import de.scoopgmbh.copper.monitoring.core.model.WorkflowInstanceInfo;
import de.scoopgmbh.copper.monitoring.core.model.WorkflowInstanceMetaData;
import de.scoopgmbh.copper.monitoring.core.model.WorkflowInstanceState;
import de.scoopgmbh.copper.monitoring.core.model.WorkflowRepositoryInfo;
import de.scoopgmbh.copper.monitoring.core.model.WorkflowRepositoryInfo.WorkflowRepositorTyp;
import de.scoopgmbh.copper.monitoring.core.model.WorkflowStateSummary;
import de.scoopgmbh.copper.monitoring.core.model.WorkflowSummary;

public class TestDataProvider implements CopperMonitoringService {
	private static final long serialVersionUID = -509088135898037190L;

	public TestDataProvider() {
		super();
	}

	@Override
	public String getAuditTrailMessage(long id) throws RemoteException {
		String json = "{\r\n" + 
				"		    \"firstName\": \"John\",\r\n" + 
				"		    \"lastName\": \"Smith\",\r\n" + 
				"		    \"age\": 25,\r\n" + 
				"		    \"address\": {\r\n" + 
				"		        \"streetAddress\": \"21 2nd Street\",\r\n" + 
				"		        \"city\": \"New York\",\r\n" + 
				"		        \"state\": \"NY\",\r\n" + 
				"		        \"postalCode\": 10021\r\n" + 
				"		    },\r\n" + 
				"		    \"phoneNumber\": [\r\n" + 
				"		        {\r\n" + 
				"		            \"type\": \"home\",\r\n" + 
				"		            \"number\": \"212 555-1234\"\r\n" + 
				"		        },\r\n" + 
				"		        {\r\n" + 
				"		            \"type\": \"fax\",\r\n" + 
				"		            \"number\": \"646 555-4567\"\r\n" + 
				"		        }\r\n" + 
				"		    ]\r\n" + 
				"		}";
		
		String xml = 
				"<?xml version=\"1.0\"?>\r\n" + 
				"<note>\r\n" + 
				"    <to>Tove</to>\r\n" + 
				"    <from>Jani</from>\r\n" + 
				"    <heading>Reminder</heading>\r\n" + 
				"    <body>Don't forget me this weekend!</body>\r\n" + 
				"</note>\r\n" + 
				"";
		
		String text = "blablablbalabl";
		
		int rnd = new Random().nextInt(3);
		if (rnd==0){
			return json;
		}
		if (rnd==1){
			return xml;
		}
		if (rnd==2){
			return text;
		}
		return "";
	}

	@Override
	public List<WorkflowSummary> getWorkflowSummary(String poolid, String classname) throws RemoteException {	
		Map<WorkflowInstanceState,Integer> map = new HashMap<WorkflowInstanceState,Integer>();
		for (WorkflowInstanceState workflowInstanceState: WorkflowInstanceState.values()){
			map.put(workflowInstanceState, (int)(Math.random()*100));
		}
		
		ArrayList<WorkflowSummary> result = new ArrayList<WorkflowSummary>();
		WorkflowSummary workflowSummery = new WorkflowSummary("",10,
				new WorkflowClassMetaData("WorkflowClass1","alias",0L,+(long)(Math.random()*100),0L,""),
				new WorkflowStateSummary(map));
		result.add(workflowSummery);
		
		return result;
	}

	@Override
	public List<WorkflowInstanceInfo> getWorkflowInstanceList(String poolid, String classname,
			WorkflowInstanceState state, Integer priority, Date form, Date to, long resultRowLimit) throws RemoteException {
		ArrayList<WorkflowInstanceInfo> result = new ArrayList<WorkflowInstanceInfo>();
		WorkflowInstanceInfo workflowInfo = new WorkflowInstanceInfo();
		workflowInfo.setId("1");
		result.add(workflowInfo);
		workflowInfo = new WorkflowInstanceInfo();
		workflowInfo.setId("2");
		result.add(workflowInfo);
		workflowInfo = new WorkflowInstanceInfo();
		workflowInfo.setId("3");
		workflowInfo.setTimeout(new Date());
		result.add(workflowInfo);
		
		return result;
	}

	@Override
	public List<AuditTrailInfo> getAuditTrails(String workflowClass, String workflowInstanceId, String correlationId, Integer level, long resultRowLimit)
			throws RemoteException {
		ArrayList<AuditTrailInfo> result = new ArrayList<AuditTrailInfo>();
		AuditTrailInfo auditTrailInfo = new AuditTrailInfo();
		auditTrailInfo.setId(1);
		auditTrailInfo.setLoglevel(1);
		result.add(auditTrailInfo);
		auditTrailInfo = new AuditTrailInfo();
		auditTrailInfo.setId(2);
		auditTrailInfo.setLoglevel(2);
		result.add(auditTrailInfo);
		auditTrailInfo = new AuditTrailInfo();
		auditTrailInfo.setId(3);
		auditTrailInfo.setLoglevel(3);
		auditTrailInfo.setOccurrence(new Date());
		auditTrailInfo.setMessageType("json");
		result.add(auditTrailInfo);
		return result;
	}

	@Override
	public List<WorkflowClassMetaData> getWorkflowClassesList(final String engineId) throws RemoteException {
		ArrayList<WorkflowClassMetaData> result = new ArrayList<WorkflowClassMetaData>();
		result.add(new WorkflowClassMetaData("WorkflowClass1","alias",0L,+(long)(Math.random()*100),0L,""));
		result.add(new WorkflowClassMetaData("WorkflowClass2","alias",1L,+(long)(Math.random()*100),0L,""));
		result.add(new WorkflowClassMetaData("WorkflowClass2","alias",1L,+(long)(Math.random()*100),0L,""));
		result.add(new WorkflowClassMetaData("WorkflowClass2","alias",1L,+(long)(Math.random()*100),0L,""));
		result.add(new WorkflowClassMetaData("WorkflowClass2","alias",2L,+(long)(Math.random()*100),0L,""));
		result.add(new WorkflowClassMetaData("WorkflowClass2","alias",2L,+(long)(Math.random()*100),0L,""));
		result.add(new WorkflowClassMetaData("WorkflowClass2","alias",2L,+(long)(Math.random()*100),0L,""));
		result.add(new WorkflowClassMetaData("WorkflowClass3","alias",3L,+(long)(Math.random()*100),0L,""));
		return result;
	}

	@Override
	public WorkflowInstanceMetaData getWorkflowInstanceDetails(String workflowInstanceId, String engineid) {
		return new WorkflowInstanceMetaData(new WorkflowClassMetaData("WorkflowClass1","alias",0L,+(long)(Math.random()*100),0L,
				"package de.scoopgmbh.copper.monitoring.example.workflow;\r\n" + 
				"\r\n" + 
				"import java.math.BigDecimal;\r\n" + 
				"import java.util.ArrayList;\r\n" + 
				"import java.util.Date;\r\n" + 
				"\r\n" + 
				"import de.scoopgmbh.copper.AutoWire;\r\n" + 
				"import de.scoopgmbh.copper.InterruptException;\r\n" + 
				"import de.scoopgmbh.copper.Response;\r\n" + 
				"import de.scoopgmbh.copper.WaitMode;\r\n" + 
				"import de.scoopgmbh.copper.Workflow;\r\n" + 
				"import de.scoopgmbh.copper.WorkflowDescription;\r\n" + 
				"import de.scoopgmbh.copper.audit.AuditTrail;\r\n" + 
				"import de.scoopgmbh.copper.monitoring.example.adapter.Bill;\r\n" + 
				"import de.scoopgmbh.copper.monitoring.example.adapter.BillAdapter;\r\n" + 
				"import de.scoopgmbh.copper.monitoring.example.adapter.BillableService;\r\n" + 
				"import de.scoopgmbh.copper.persistent.PersistentWorkflow;\r\n" + 
				"\r\n" + 
				"@WorkflowDescription(alias=\"BillWorkflow\", majorVersion=1, minorVersion=0, patchLevelVersion=0)\r\n" + 
				"public class WorkflowClass1 extends PersistentWorkflow<String> {\r\n" + 
				"	private static final long serialVersionUID = 1L;\r\n" + 
				"\r\n" + 
				"	private transient BillAdapter billAdapter;\r\n" + 
				"	private transient AuditTrail auditTrail;\r\n" + 
				"	\r\n" + 
				"	private ArrayList<BillableService> billableServices= new ArrayList<BillableService>();\r\n" + 
				"\r\n" + 
				"	@AutoWire\r\n" + 
				"	public void setBillAdapter(BillAdapter billAdapter) {\r\n" + 
				"		this.billAdapter = billAdapter;\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	@AutoWire\r\n" + 
				"	public void setAuditTrail(AuditTrail auditTrail) {\r\n" + 
				"		this.auditTrail = auditTrail;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	@Override\r\n" + 
				"	public void main() throws InterruptException {\r\n" + 
				"		while (true){\r\n" + 
				"			auditTrail.asynchLog(2, new Date(), \"\", \"\", \"\", \"\", \"\", \"wait for Data\", \"Text\");\r\n" + 
				"			wait(WaitMode.ALL,Workflow.NO_TIMEOUT, BillAdapter.BILL_TIME,BillAdapter.BILLABLE_SERVICE);\r\n" + 
				"			auditTrail.asynchLog(1, new Date(), \"\", \"\", \"\", \"\", \"\", \"data found\", \"Text\");\r\n" + 
				"			\r\n" + 
				"			ArrayList<Response<?>> all = new ArrayList<Response<?>>(getAndRemoveResponses(BillAdapter.BILL_TIME));\r\n" + 
				"			all.addAll(getAndRemoveResponses(BillAdapter.BILLABLE_SERVICE));\r\n" + 
				"			\r\n" + 
				"			Response<String> rsponse = new Response<String>(\"cor\",\"message\",null);\r\n" + 
				"			rsponse.getResponse();\r\n" + 
				"			\r\n" + 
				"			\r\n" + 
				"			for(Response<?> response: all){\r\n" + 
				"				if (response.getResponse() instanceof BillableService){\r\n" + 
				"					billableServices.add(((BillableService)response.getResponse()));\r\n" + 
				"				}\r\n" + 
				"			}\r\n" + 
				"			for(Response<?> response: all){\r\n" + 
				"				if (response.getResponse() instanceof Bill){\r\n" + 
				"					Bill bill = ((Bill)response.getResponse());\r\n" + 
				"					BigDecimal sum = new BigDecimal(0);\r\n" + 
				"					for (BillableService billableService: billableServices){\r\n" + 
				"						sum = sum.add(billableService.getAmount());\r\n" + 
				"					}\r\n" + 
				"					bill.setTotalAmount(sum);\r\n" + 
				"					billAdapter.publishBill(bill);\r\n" + 
				"					billableServices.clear();\r\n" + 
				"				}\r\n" + 
				"			}\r\n" + 
				"			\r\n" + 
				"			\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"}"));
		
		
	}

	@Override
	public CopperInterfaceSettings getSettings() throws RemoteException {
		return new CopperInterfaceSettings(true);
	}

	@Override
	public List<String[]> executeSqlQuery(String query, long resultRowLimit) {
		List<String[]>  result = new ArrayList<String[]>();
		result.add(new String[]{"column1","column2",query});
		return result;
	}
	
	@Override
	public SystemResourcesInfo getSystemResourceInfo() throws RemoteException {
		return new SystemResourcesInfo(new Date(),0,0,0,0,0,0);
	}

	@Override
	public WorkflowStateSummary getAggregatedWorkflowStateSummary(String engineid) throws RemoteException {
		Map<WorkflowInstanceState,Integer> map = new HashMap<WorkflowInstanceState,Integer>();
		for (WorkflowInstanceState workflowInstanceState: WorkflowInstanceState.values()){
			map.put(workflowInstanceState, (int)(Math.random()*100));
		}
		return new WorkflowStateSummary(map);
	}

	@Override
	public void restartWorkflowInstance(String workflowInstanceId, String engineid) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restartAllErroneousInstances(String engineid) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProcessingEngineInfo> getProccessingEngineList() throws RemoteException {
		WorkflowRepositoryInfo repositoryInfo = new WorkflowRepositoryInfo();
		repositoryInfo.setWorkflowRepositorTyp(WorkflowRepositorTyp.FILE);
		repositoryInfo.setSrcPaths(new ArrayList<String>());
		return Arrays.asList(
				new ProcessingEngineInfo(EngineTyp.PERSISTENT,"peId1",repositoryInfo,new DependencyInjectorInfo(DependencyInjectorTyp.POJO),new StorageInfo(), new ProcessorPoolInfo("poId1",ProcessorPoolTyp.PERSISTENT)),
				new ProcessingEngineInfo(EngineTyp.TRANSIENT,"peId2",repositoryInfo,new DependencyInjectorInfo(DependencyInjectorTyp.POJO),new StorageInfo(), new ProcessorPoolInfo("poId2",ProcessorPoolTyp.TRANSIENT), new ProcessorPoolInfo("poId3",ProcessorPoolTyp.TRANSIENT))
				);
	}


	@Override
	public List<MeasurePointData> getMeasurePoints(String engineid) {
		ArrayList<MeasurePointData> result = new ArrayList<MeasurePointData>();
		for (int i=0;i<20;i++){
			result.add(new MeasurePointData("point dhajsgdjahdgsdjasdgjasgdhjgfhjsgfjshd"+i,10,i+(int)(10000*Math.random()),10));
		}
		return result;
	}

	public int numberOfThreads;
	@Override
	public void setNumberOfThreads(String engineid, String processorPoolId, int numberOfThreads) {
		this.numberOfThreads=numberOfThreads;
	}

	public int threadPriority;
	@Override
	public void setThreadPriority(String engineid, String processorPoolId, int threadPriority) {
		this.threadPriority = threadPriority;
	}


	@Override
	public void resetMeasurePoints() {
		// TODO Auto-generated method stub
	}
	
	public int numerOfThreadsBatcher;
	@Override
	public void setBatcherNumThreads(int numThread, String engineid) {
		this.numerOfThreadsBatcher = numThread;
	}


	@Override
	public List<MessageInfo> getMessageList(boolean ignoreproceeded ,long resultRowLimit) {
		return Collections.emptyList();
	}

	@Override
	public AdapterHistoryInfo getAdapterHistoryInfos(String adapterId) throws RemoteException {
		return new AdapterHistoryInfo();
	}

	@Override
	public List<MeasurePointData> getMonitoringMeasurePoints(String measurePoint,long limit) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMonitoringMeasurePointIds() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogData getLogData() throws RemoteException {
		// TODO Auto-generated method stub
		return new LogData(Arrays.asList(new LogEvent(new Date(), "test", "WorkflowClass1(line: 20)", "ERROR")), "# Set root logger level to DEBUG and its only appender to A1.\r\n" + 
				"log4j.rootLogger=DEBUG, A1\r\n" + 
				"\r\n" + 
				"# A1 is set to be a ConsoleAppender.\r\n" + 
				"log4j.appender.A1=org.apache.log4j.ConsoleAppender");
	}

	@Override
	public void updateLogConfig(String config) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearLogData() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDatabaseMonitoringHtmlReport() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDatabaseMonitoringHtmlDetailReport(String sqlid) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDatabaseMonitoringRecommendationsReport(String sqlid) throws RemoteException {
		return "";
	}
}
