package caroclient.model;

public class Account {
	private String id;
	private String email;
	private String fullname;
	private int gender;
	private String birthday;

	public Account(String id, String email, String fullname, int gender, String birthday) {
		this.id = id;
		this.email = email;
		this.fullname = fullname;
		this.gender = gender;
		this.birthday = birthday;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFullname() {
		return fullname;
	}

	public int getGender() {
		return gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
