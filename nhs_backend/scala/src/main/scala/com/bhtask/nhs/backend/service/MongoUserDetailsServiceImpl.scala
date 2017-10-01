package com.bhtask.nhs.backend.service

import com.bhtask.nhs.backend.dao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.security.core.userdetails.{User, UserDetails, UserDetailsService}
import org.springframework.stereotype.Service

@Service("mongoUserDetailsServiceImpl")
class MongoUserDetailsServiceImpl(@Autowired val mongoOps: MongoOperations) extends UserDetailsService {

  override def loadUserByUsername(s: String): UserDetails = {
    val userQuery = query(where(dao.User.FIELD__USERNAME).is(s))
    if (mongoOps.exists(userQuery, classOf[dao.User])) {
      val user = mongoOps.findOne(userQuery, classOf[dao.User])
      return User.withUsername(user.getUsername).password(user.getPassword).authorities(user.getAuthorities: _*).build()
    }
    null
  }
}
