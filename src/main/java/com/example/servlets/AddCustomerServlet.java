package com.example.servlets;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/addCustomer")
public class AddCustomerServlet extends HttpServlet {
    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String pass = "password";
        return DriverManager.getConnection(url, user, pass);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson gson = new Gson();
        Map<String, Object> result = new HashMap<>();
        try(BufferedReader reader = req.getReader();
            Connection con = getConnection()){
            Map<?,?> data = gson.fromJson(reader, Map.class);
            PreparedStatement ps = con.prepareStatement("INSERT INTO account_holder(ssn,name,email,password) VALUES(?,?,?,?)");
            ps.setString(1, (String)data.get("ssn"));
            ps.setString(2, (String)data.get("name"));
            ps.setString(3, (String)data.get("email"));
            ps.setString(4, (String)data.get("password"));
            ps.executeUpdate();
            result.put("success", true);
        }catch(Exception e){
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.flush();
    }
}
