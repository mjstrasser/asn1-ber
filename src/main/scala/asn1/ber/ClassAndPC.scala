package asn1.ber

/**
 * Encapsulation of the class and primitive/constructed information
 * from the identifier octets of a data value.
 *
 * @param octet the first of the identifier octets of an encoded
 *              data value
 */
class ClassAndPC(val octet: Byte) {
  def this(byteAsInt: Int) = this(byteAsInt.toByte)
  def isUniversal = (octet & 0xE0) == Ber.Universal
  def isApplication = (octet & Ber.Private) == Ber.Application
  def isContextSpecific = (octet & Ber.Private) == Ber.ContextSpecific
  def isPrivate = (octet & Ber.Private) == Ber.Private
  def isConstructed = (octet & Ber.Constructed) == Ber.Constructed
  def isPrimitive = (octet & Ber.Constructed) != Ber.Constructed
  def toBytes = Seq(octet)
  override def equals(other: Any) = other match {
    case that: ClassAndPC => this.octet == that.octet
    case _ => false
  }
  override def hashCode = 41 * (octet.hashCode + 41)
  def canEqual(other: Any) = other.isInstanceOf[ClassAndPC]
  override def toString = s"ClassAndPC($octet)"
}

object ClassAndPC {
  def apply(idByte: Byte) = new ClassAndPC((idByte & 0xE0).toByte)
}

