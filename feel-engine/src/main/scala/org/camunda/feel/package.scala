package org.camunda

import scala.math.BigDecimal
import java.time._
import java.time.format._
import java.time.temporal.ChronoField._

/**
 * @author Philipp Ossler
 */
package object feel {

  type Number = BigDecimal

  type Date = java.time.LocalDate

  type LocalTime = java.time.LocalTime

  type LocalDateTime = java.time.LocalDateTime

  type Time = java.time.OffsetTime

  type DateTime = java.time.OffsetDateTime

  type YearMonthDuration = java.time.Period

  type DayTimeDuration = java.time.Duration

  import scala.language.implicitConversions

  implicit def stringToNumber(number: String): Number = BigDecimal(number)

  implicit def stringToDate(date: String): Date = LocalDate.parse(date, dateFormatter)

  implicit def stringToLocalTime(time: String): LocalTime = LocalTime.parse(time, timeFormatterWithOptionalPrefix)

  implicit def stringToTime(time: String): Time = OffsetTime.parse(time, timeFormatterWithOffsetAndOptionalPrefix)

  implicit def stringToLocalDateTime(dateTime: String): LocalDateTime = LocalDateTime.parse(dateTime, localDateTimeFormatter)

  implicit def stringToDateTime(dateTime: String): DateTime = OffsetDateTime.parse(dateTime, dateTimeFormatter)

  implicit def stringToYearMonthDuration(duration: String): YearMonthDuration = Period.parse(duration)

  implicit def stringToDayTimeDuration(duration: String): DayTimeDuration = Duration.parse(duration)

  def isOffsetTime(time: String): Boolean = time matches("""T?\d{2}:\d{2}:\d{2}[+-]\d{2}:\d{2}""")

  def isOffsetDateTime(dateTime: String): Boolean = dateTime matches("""\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}[+-]\d{2}:\d{2}""")

  def isYearMonthDuration(duration: String): Boolean = duration matches("""P(\d+Y)?(\d+M)?""")

  val timeFormatterWithPrefix = new DateTimeFormatterBuilder()
		.appendLiteral('T')
    .appendValue(HOUR_OF_DAY, 2)
    .appendLiteral(':')
    .appendValue(MINUTE_OF_HOUR, 2)
    .optionalStart()
    .appendLiteral(':')
    .appendValue(SECOND_OF_MINUTE, 2)
    .appendFraction(NANO_OF_SECOND, 0, 9, true)
    .toFormatter();
	
	val timeFormatterWithOptionalPrefix = new DateTimeFormatterBuilder()
		.optionalStart()
    .appendLiteral('T')
    .optionalEnd()
    .appendValue(HOUR_OF_DAY, 2)
    .appendLiteral(':')
    .appendValue(MINUTE_OF_HOUR, 2)
    .optionalStart()
    .appendLiteral(':')
    .appendValue(SECOND_OF_MINUTE, 2)
    .appendFraction(NANO_OF_SECOND, 0, 9, true)
    .toFormatter();

  val timeFormatterWithOffsetAndOptionalPrefix = new DateTimeFormatterBuilder()
		.append(timeFormatterWithOptionalPrefix)
    .appendOffsetId()
    .toFormatter();

  val localTimeFormatter = new DateTimeFormatterBuilder()
		.appendValue(HOUR_OF_DAY, 2)
    .appendLiteral(':')
    .appendValue(MINUTE_OF_HOUR, 2)
    .optionalStart()
    .appendLiteral(':')
    .appendValue(SECOND_OF_MINUTE, 2)
    .appendFraction(NANO_OF_SECOND, 0, 9, true)
    .toFormatter();
  
  val timeFormatter = new DateTimeFormatterBuilder()
    .append(localTimeFormatter)
    .appendOffsetId()
    .toFormatter();
  
  val dateFormatter = new DateTimeFormatterBuilder()
    .appendValue(YEAR, 4, 9, SignStyle.NORMAL)
    .appendLiteral("-")
    .appendValue(MONTH_OF_YEAR, 2)
    .appendLiteral("-")
    .appendValue(DAY_OF_MONTH, 2)
    .toFormatter();
    
  val localDateTimeFormatter = new DateTimeFormatterBuilder()
    .append(dateFormatter)
    .append(timeFormatterWithPrefix)
    .toFormatter();
	
  val dateTimeFormatter = new DateTimeFormatterBuilder()
    .append(dateFormatter)
    .append(timeFormatterWithPrefix)
    .appendOffsetId()
    .toFormatter();
  
}
