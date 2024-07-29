package jdbc_00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class jdbc_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final String driver = "org.mariadb.jdbc.Driver"; //드라이버 연결 설정, 마리아DB드라이버 주소
		final String db_ip = "localhost";
		final String db_port = "3306";
		final String db_name = "student_test";
		final String db_url = 
				"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name; // 접속할 데이터베이스 정보 입력

		Connection conn = null;  // 데이터베이스 접속,연결
		PreparedStatement pstmt = null;  // 쿼리실행
		ResultSet rs = null;  // 결과

		try { //데이터베이스 접속,연결
			Class.forName(driver); //드라이버
			conn = DriverManager.getConnection(db_url, "root", "1234"); 
			if(conn != null) {
				System.out.println("접속성공");
			} 
		} catch(ClassNotFoundException e) { 
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch(SQLException e) {
			System.out.println("DB 접속 실패");
			e.printStackTrace();
		}
		
		try { //쿼리실행
			String sql ="SELECT school_id, school_name, school_area " + "FROM tb_school_info "; //쿼리 선언
//							+ "WHERE school_id =1"; 한건만 조회할 때
			
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql); //쿼리 정보 담는 변수

			rs = pstmt.executeQuery(); // 담은 쿼리를 실행하고 결과 값을 rs 에 담기
//			int schoolId = 0;
//			String schoolName = null;
//			String schoolArea = null;
			
			List<HashMap<String, Object>> list = new ArrayList();

			while(rs.next()) { // rs 에 있는 결과값을 하나씩 확인(돌려보기)
//				schoolId = rs.getInt(1);
//				schoolName = rs.getString("school_name");
//				schoolArea = rs.getString(3);
				
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("schoolId", rs.getInt(1));
				rsMap.put("schoolName", rs.getString(2));
				rsMap.put("schoolArea", rs.getString(3));
				list.add(rsMap);
			}
//			System.out.println("schoolId : " + schoolId);
//			System.out.println("schoolName : " + schoolName);
//			System.out.println("schoolArea : " + schoolArea);
			System.out.println("학교ID \t학교이름\t학교지역");
			for (int i=0; i<list.size(); i++) {
				System.out.println(list.get(i).get("schoolId").toString()+"\t"+ list.get(i).get("schoolName").toString()+"\t"+ list.get(i).get("schoolArea"));
			}
		}catch(SQLException e) {
			System.out.println("error : " + e);
		}finally { // 다 사용한 데이터베스를 닫아주기
			try {
				if(rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null && conn.isClosed()) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
