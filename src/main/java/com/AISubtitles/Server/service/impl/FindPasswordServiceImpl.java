package com.AISubtitles.Server.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AISubtitles.Server.dao.FindPasswordDao;
import com.AISubtitles.Server.entity.User;
import com.AISubtitles.Server.service.FindPasswordService;
import com.AISubtitles.Server.util.Result;

/**
 * @author Gavin
 * @desc FindPasswordServiceImpl
 */
@Service
public class FindPasswordServiceImpl implements FindPasswordService {

	@Autowired
	private FindPasswordDao findPasswordDao;

	User user = new User();

	/**
	 * 修改用户密码
	 */
	@Override
	public Result<User> update(String password, String newpassword) {
		Result<User> resultUser = new Result<>();
		if (password.equals(newpassword)) {
			Integer success = findPasswordDao.update(newpassword, user.getId());
			if (success > 0) {
				resultUser.setCode(200);
			}
			return resultUser;
		} else {
			resultUser.setCode(500);
			resultUser.setMessage("两次密码输入的不一致！");
			return resultUser;
		}
	}

	@Override
	public Result<User> validateCode(String emailCode, HttpSession session) {
		Result<User> resultUser = new Result<>();
		String code = (String) session.getAttribute("code");
		if (code.equals(emailCode)) {
			resultUser.setCode(200);
			resultUser.setMessage("");
			return resultUser;
		} else {
			resultUser.setCode(500);
			resultUser.setMessage("验证码错误");
			return resultUser;
		}

	}

	/**
	 * 查询用户信息
	 */
	@Override
	public Result<User> select(HttpServletRequest request) {
		Result<User> resultUser = new Result<>();
		String qqnum = request.getParameter("qqnum");
		user = findPasswordDao.select(qqnum);
		int qq = findPasswordDao.selectUserCount(qqnum);
		if (qq > 0) {
			resultUser.setCode(200);
			resultUser.setData(user);
			return resultUser;
		} else {
			resultUser.setCode(500);
			resultUser.setMessage("您录入的账号不存在！");
			return resultUser;
		}
	}
}
