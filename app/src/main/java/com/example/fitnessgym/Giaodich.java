package com.example.fitnessgym;

import java.io.Serializable;

public class Giaodich implements Serializable {
 String userName;
 int user_id;
 int id;
 String type;
 String price;

 public Giaodich(String userName, int user_id, int id, String type, String price) {
  this.userName = userName;
  this.user_id = user_id;
  this.id = id;
  this.type = type;
  this.price = price;
 }

 public String getUserName() {
  return userName;
 }

 public void setUserName(String userName) {
  this.userName = userName;
 }

 public int getUser_id() {
  return user_id;
 }

 public void setUser_id(int user_id) {
  this.user_id = user_id;
 }

 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }

 public String getType() {
  return type;
 }

 public void setType(String type) {
  this.type = type;
 }

 public String getPrice() {
  return price;
 }

 public void setPrice(String price) {
  this.price = price;
 }
}
