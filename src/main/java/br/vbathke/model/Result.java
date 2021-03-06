package br.vbathke.model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import br.vbathke.helper.SqliteHelper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class Result {

	private int idJob = 0;
	private int idExec = 0;
	private String test = "";
	private String status = "";
	private String description = "";
	private String stacktrace = "";
	private String date = "";
	
	public Result(){		
	}
		
	@SuppressFBWarnings("SQL_NONCONSTANT_STRING_PASSED_TO_EXECUTE")
	public Result(int pIdJob, int pIdExec, String pTest){
		try {
			this.setIdJob(pIdJob);
			this.setIdExec(pIdExec);
			this.setTest(pTest);
	    	SqliteHelper conn = new SqliteHelper();
	    	JSONArray rs;
			rs = conn.query( "SELECT * FROM tb_result where id_job='"+pIdJob+"' and id_exec='"+idExec+"' and test='"+test+"';" );
			if(rs.size()>0){				
				JSONObject item = rs.getJSONObject(0);
			    if(item.getString("test").equals(test)){
			    	setIdExec(idExec);
				    setIdJob(rs.getJSONObject(0).getInt("id_job"));
				    setTest(rs.getJSONObject(0).getString("test"));
				    setStatus(rs.getJSONObject(0).getString("status"));
				    setDescription(rs.getJSONObject(0).getString("description"));
				    setStacktrace(rs.getJSONObject(0).getString("stacktrace"));
				    setDate(rs.getJSONObject(0).getString("date"));
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
	}
	
	@SuppressFBWarnings({"SQL_NONCONSTANT_STRING_PASSED_TO_EXECUTE","OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE"})
	public void save(){
        Statement stmt = null;
		try {
	        String testeAtual = "";
	        SqliteHelper conn = new SqliteHelper();
	        JSONArray rs = conn.query( "SELECT * FROM tb_result where id_exec='"+getIdExec()+"' and test='"+getTest()+"';" );
			for (int i=0; i<rs.size(); i++) {
				JSONObject item = rs.getJSONObject(i);
			    if(item.getString("test").equals(test)){
			    	testeAtual= test;
			    }
			}
			if(testeAtual.equals("")){
				conn.update("INSERT INTO tb_result(id_job, id_exec, test, status, stacktrace, date) VALUES('"+getIdJob()+"','"+getIdExec()+"','"+getTest()+"','"+this.getStatus()+"','"+this.getStacktrace().replace("'", "''")+"','"+(new SimpleDateFormat("yyyyMMddHHmmss")).format(new java.util.Date())+"');");
			}else{
			    stmt = SqliteHelper.getConn().createStatement();	    	
			    stmt.executeUpdate("update tb_result set "
			    		+ "description='"+this.getDescription()+"' "
			    		+ "where id_exec='"+this.getIdExec()+"' "
			    		+ " and test='"+this.getTest()+"' ");				
				stmt.close();
			}
		} catch (Exception e) {
			stmt = null;
			e.printStackTrace();
		}
	}
	
	public int getIdJob() {
		return idJob;
	}
	public void setIdJob(int idJob) {
		this.idJob = idJob;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public int getIdExec() {
		return idExec;
	}

	public void setIdExec(int idExec) {
		this.idExec = idExec;
	}
	
}
