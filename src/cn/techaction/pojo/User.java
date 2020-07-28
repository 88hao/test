//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.pojo;

import java.util.Date;

public class User {
    private Integer id;
    private String account;
    private String password;
    private String email;
    private Integer age;
    private Integer sex;
    private Integer del;
    private String phone;
    private String question;
    private String asw;
    private Integer role;
    private Date create_time;
    private Date update_time;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
    }

    public User(Integer id, String account, String password, String email, Integer age, Integer sex, Integer del, String phone, String question, String asw, Integer role, Date create_time, Date update_time, String name) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.del = del;
        this.phone = phone;
        this.question = question;
        this.asw = asw;
        this.role = role;
        this.create_time = create_time;
        this.update_time = update_time;
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getDel() {
        return this.del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAsw() {
        return this.asw;
    }

    public void setAsw(String asw) {
        this.asw = asw;
    }

    public Integer getRole() {
        return this.role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
