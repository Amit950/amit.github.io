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
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String pass = "password"; // change as needed
        return DriverManager.getConnection(url, user, pass);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson gson = new Gson();
        Map<String, Object> result = new HashMap<>();
        try(BufferedReader reader = req.getReader();
            Connection con = getConnection()) {
            Map<?,?> data = gson.fromJson(reader, Map.class);
            String userId = (String)data.get("userId");
            String password = (String)data.get("password");
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM employee_login WHERE user_id=? AND password=?");
            ps.setString(1, userId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next() && rs.getInt(1) > 0){
                result.put("success", true);
            }else{
                result.put("success", false);
                result.put("message", "Invalid credentials");
            }
        }catch(Exception e){
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.flush();
    }
}
