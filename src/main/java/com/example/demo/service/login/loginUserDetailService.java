//package com.example.demo.service.login;
//
//import com.example.demo.dao.RestaurantDB;
//import com.example.demo.model.Restaurant;
//import com.example.demo.model.login.UserPrinciple;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class loginUserDetailService implements UserDetailsService {
//    @Autowired
//    RestaurantDB db;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Restaurant user= db.findByUsername(username);
//        if(user==null){
//            System.out.println("No user");
//        }
//        return new UserPrinciple(user);
//}}
