package com.example.servlets;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/getCustomer")
public class GetCustomerServlet extends HttpServlet {
    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String pass = "password";
        return DriverManager.getConnection(url, user, pass);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson gson = new Gson();
        Map<String,String> c = new HashMap<>();
        String ssn = req.getParameter("ssn");
        try(Connection con = getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT ssn,name,email FROM account_holder WHERE ssn=?");
            ps.setString(1, ssn);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.put("ssn", rs.getString("ssn"));
                c.put("name", rs.getString("name"));
                c.put("email", rs.getString("email"));
            }
        }catch(Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(c));
        out.flush();
    }
}
