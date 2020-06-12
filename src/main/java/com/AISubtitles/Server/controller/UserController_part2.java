// package com.AISubtitles.Server.controller;

// import java.util.NoSuchElementException;

// import com.AISubtitles.Server.dao.UserDao;
// import com.AISubtitles.Server.dao.UserModificationDao;
// import com.AISubtitles.Server.domain.Result;
// import com.AISubtitles.Server.domain.User;
// import com.AISubtitles.Server.domain.UserModification;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class UserController_part2 {
//     @Autowired
//     UserDao userDao;


//     @Autowired
//     UserModificationDao userModificationDao;

//     //操作时间字段由数据库自动记录
//     public void add_user_modification_record(int userId, String fieldName,
//                                             String oldValue, String newValue) {
//         UserModification um = new UserModification();
//         um.setUserId(userId);
//         um.setFieldName(fieldName);
//         um.setOldValue(oldValue);
//         um.setNewValue(newValue);
//         userModificationDao.save(um);
//     }

//     @GetMapping("/test")
//     public User insertUser(User user) {
//         userDao.save(user);
//         add_user_modification_record(user.getUserId(), "new", user.getUserName(), user.getUserName());
//         return user;
//     }

//     @PostMapping(value = "user/motify4person")
//     public Result motify4person(int userId, String fieldName, String oldValue, String newValue) {
//         Result<User> result = new Result<User>();
//         try{
//             User user = userDao.findById(userId).get();
//         } catch (NoSuchElementException e) {
//             result.setCode(500);
//             result.setStatus(608);
//             result.setData(null);
//             return result;
//         }
        
//         //userId，image, userPassword不在修改范围内
//         String[] field = new String[]{"userName", "userGender", "userBirthday", "userEmail", "userPhoneNumber"};
//         int i;
//         for(i = 0; i < field.length; i++) {
//             if(fieldName.equals(field[i])) {
//                 // userDao.updateUserInfo(fieldName, newValue, userId);
//                 break;
//             }
//         }
//         if(i == field.length) {
//             result.setCode(500);
//             result.setStatus(607);
//             result.setData(null);
//             return result;
//         }

//         // add_user_modification_record(userId, fieldName, oldValue, newValue);
//         result.setCode(200);
//         result.setStatus(200);
//         result.setData(userDao.findById(userId).get());
//         return result;
//     }

// }