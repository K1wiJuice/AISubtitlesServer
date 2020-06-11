package com.AISubtitles.Server.findpassword.entity;



/**
 * 
 * @author Gavin
 *  此类虚与domain.User整合
 */

public class User {

    // 用户ID
    private String id;

    // 用户姓名
    private String realName;

    // 性别
    private String sex;

    // 年龄
    private Integer age;

    // 昵称
    private String nickname;

    // qq号
    private Integer qqNum;

    // 手机号
    private String phoneNum;

    // 班级号
    //private String classNum;

    // 生日
    private String birthday;

    // 角色
    private Integer roleId;

    // 邮箱
    private String email;
    // 身份证号码
    private String IDCard;
    // 爱好
    private String hobby;
    // 学校
    private String studSchool;
    // 省编码
    private String provinceNum;
    // 省名称
    private String provinceName;
    // 市编码
    private String cityNum;
    // 市名称
    private String cityName;
    // 区编码
    private String countryNum;
    // 区名称
    private String countryName;
    // 工作单位
    private String employer;

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String iDCard) {
        IDCard = iDCard;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getStudSchool() {
        return studSchool;
    }

    public void setStudSchool(String studSchool) {
        this.studSchool = studSchool;
    }

    public String getProvinceNum() {
        return provinceNum;
    }

    public void setProvinceNum(String provinceNum) {
        this.provinceNum = provinceNum;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityNum() {
        return cityNum;
    }

    public void setCityNum(String cityNum) {
        this.cityNum = cityNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryNum() {
        return countryNum;
    }

    public void setCountryNum(String countryNum) {
        this.countryNum = countryNum;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /*public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }*/

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getQqNum() {
        return qqNum;
    }

    public void setQqNum(Integer qqNum) {
        this.qqNum = qqNum;
    }

}
