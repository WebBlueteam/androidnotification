package com.example.comment;

public class item_comment {
	public int Id;// id item_listview
	public String User_name;// ten nguoi commnt
	public String Time_comment;// thoi gian comment
	public String Content_comment;// noi dung comment
	public int Id_avata;
	public item_comment(int id,String user,String Time,String content,int avata){
		Id=id;
		User_name=user;
		Time_comment=Time;
		Content_comment=content;
		Id_avata=avata;
	}
}
