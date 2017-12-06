package com.mvc.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mvc.util.DBConnection;
import com.mvc.util.SendEmail;

/**
 * Servlet implementation class AddSpace
 */
@WebServlet("/AddSpace")
public class AddSpace extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddSpace() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String pName, pState, pAdd, pCity, pZip, pOpenTime, pCloseTime, pVehicles, id, data, ownerName;
		JSONArray vehicles;
		Connection connection = null;
		PreparedStatement pStatement = null;
		Statement statement = null, statement2 = null;
		int parkingId, ownerId;
		ResultSet rs, rs2;
		int vtId;

		try {
			pName = request.getParameter("pName");
			pState = request.getParameter("state");
			pAdd = request.getParameter("address");
			pCity = request.getParameter("city");
			pZip = request.getParameter("zipcode");
			pOpenTime = request.getParameter("oTime");
			pCloseTime = request.getParameter("cTime");
			pVehicles = request.getParameter("allVehicles");
			id = (String) request.getSession().getAttribute("user_id");

			connection = DBConnection.createConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(
					"select PO_id,PO_fullName from parkingowner where Users_user_id=" + Integer.parseInt(id));
			if (rs.next()) {
				ownerId = rs.getInt("PO_id");
				ownerName = rs.getString("PO_fullName");
			} else {
				ownerId = 1;
				ownerName = "Parking Owner";
			}

			data = pName + "," + ownerName + "," + pAdd + "," + pCity + "," + pState;
			pStatement = connection.prepareStatement(
					"insert into parking (P_name,P_state,P_city,P_address,P_zip,P_openTime,P_closeTime,Owner_PO_id,Status) values(?,?,?,?,?,?,?,?,?)");
			pStatement.setString(1, pName);
			pStatement.setString(2, pState);
			pStatement.setString(3, pCity);
			pStatement.setString(4, pAdd);
			pStatement.setString(5, pZip);
			pStatement.setString(6, pOpenTime);
			pStatement.setString(7, pCloseTime);
			pStatement.setInt(8, ownerId);
			pStatement.setString(9, "pending");

			pStatement.executeUpdate();

			rs = statement.executeQuery("select P_id from parking where Owner_PO_id=" + ownerId);
			rs.last();
			parkingId = rs.getInt("P_id");

			System.out.println(pVehicles);
			System.out.println(parkingId);
			vehicles = new JSONArray(pVehicles);

			for (int i = 0; i < vehicles.length(); i++) {
				JSONObject obj = vehicles.getJSONObject(i);

				statement2 = connection.createStatement();
				rs2 = statement2.executeQuery(
						"select vt_id from vehicletype where type='" + obj.getString("VehicleType") + "'");
				pStatement = connection.prepareStatement(
						"insert into parkingspace (PS_vehicleType,PS_AvailableOnlineSlots,PS_OnlineCharges,PS_AvailableOfflineSlots,PS_OfflineCharges,PS_Tax,Parking_P_id,vehicle_vt_id) values(?,?,?,?,?,?,?,?)");
				if (rs2.next())
					vtId = rs2.getInt("vt_id");
				else
					vtId = 1;
				pStatement.setString(1, obj.getString("VehicleType"));
				pStatement.setInt(2, obj.getInt("AvailableOnlineSlots"));
				pStatement.setInt(3, obj.getInt("OnlineCharges"));
				pStatement.setInt(4, obj.getInt("AvailableOfflineSlots"));
				pStatement.setInt(5, obj.getInt("OfflineCharges"));
				pStatement.setInt(6, obj.getInt("Tax"));
				pStatement.setInt(7, parkingId);
				pStatement.setInt(8, vtId);

				pStatement.executeUpdate();

			}
			request.getRequestDispatcher("/parkingSpace.jsp").forward(request, response);

			SendEmail.sendMailToAdmin(data);
			pStatement.close();
			statement.close();
			rs.close();
			connection.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
