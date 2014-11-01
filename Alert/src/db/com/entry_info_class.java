package db.com;

import java.util.List;

public class entry_info_class {
	public String reponse;
	public List<entryinfo>entryinfo;
	public class entryinfo
	{
		public int E_id;
		public String E_title;
		public String E_content;
		public String E_date;
		public int E_view;
		public int E_Sshare;
		public String E_username;
		public String E_avatar;
		public String E_Mainimg;
	}
}
