package com.bhtask.nhs.backend.service

import com.bhtask.nhs.backend.dao.Condition

trait ConditionsService {

  def search(query: String): Iterable[Condition]

  def getAllLinks: Iterable[String]

  def getCount: Long

  def loadIndex(): Boolean
}