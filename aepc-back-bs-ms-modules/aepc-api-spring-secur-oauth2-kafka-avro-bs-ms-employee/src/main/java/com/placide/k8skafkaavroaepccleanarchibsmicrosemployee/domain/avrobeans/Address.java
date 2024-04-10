/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Address extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 4727689926929700564L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Address\",\"namespace\":\"com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans\",\"fields\":[{\"name\":\"addressId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"num\",\"type\":\"int\"},{\"name\":\"street\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"pb\",\"type\":\"int\"},{\"name\":\"city\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"country\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Address> ENCODER =
      new BinaryMessageEncoder<Address>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Address> DECODER =
      new BinaryMessageDecoder<Address>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Address> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Address> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Address> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Address>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Address to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Address from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Address instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Address fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String addressId;
  private int num;
  private java.lang.String street;
  private int pb;
  private java.lang.String city;
  private java.lang.String country;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Address() {}

  /**
   * All-args constructor.
   * @param addressId The new value for addressId
   * @param num The new value for num
   * @param street The new value for street
   * @param pb The new value for pb
   * @param city The new value for city
   * @param country The new value for country
   */
  public Address(java.lang.String addressId, java.lang.Integer num, java.lang.String street, java.lang.Integer pb, java.lang.String city, java.lang.String country) {
    this.addressId = addressId;
    this.num = num;
    this.street = street;
    this.pb = pb;
    this.city = city;
    this.country = country;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return addressId;
    case 1: return num;
    case 2: return street;
    case 3: return pb;
    case 4: return city;
    case 5: return country;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: addressId = value$ != null ? value$.toString() : null; break;
    case 1: num = (java.lang.Integer)value$; break;
    case 2: street = value$ != null ? value$.toString() : null; break;
    case 3: pb = (java.lang.Integer)value$; break;
    case 4: city = value$ != null ? value$.toString() : null; break;
    case 5: country = value$ != null ? value$.toString() : null; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'addressId' field.
   * @return The value of the 'addressId' field.
   */
  public java.lang.String getAddressId() {
    return addressId;
  }


  /**
   * Sets the value of the 'addressId' field.
   * @param value the value to set.
   */
  public void setAddressId(java.lang.String value) {
    this.addressId = value;
  }

  /**
   * Gets the value of the 'num' field.
   * @return The value of the 'num' field.
   */
  public int getNum() {
    return num;
  }


  /**
   * Sets the value of the 'num' field.
   * @param value the value to set.
   */
  public void setNum(int value) {
    this.num = value;
  }

  /**
   * Gets the value of the 'street' field.
   * @return The value of the 'street' field.
   */
  public java.lang.String getStreet() {
    return street;
  }


  /**
   * Sets the value of the 'street' field.
   * @param value the value to set.
   */
  public void setStreet(java.lang.String value) {
    this.street = value;
  }

  /**
   * Gets the value of the 'pb' field.
   * @return The value of the 'pb' field.
   */
  public int getPb() {
    return pb;
  }


  /**
   * Sets the value of the 'pb' field.
   * @param value the value to set.
   */
  public void setPb(int value) {
    this.pb = value;
  }

  /**
   * Gets the value of the 'city' field.
   * @return The value of the 'city' field.
   */
  public java.lang.String getCity() {
    return city;
  }


  /**
   * Sets the value of the 'city' field.
   * @param value the value to set.
   */
  public void setCity(java.lang.String value) {
    this.city = value;
  }

  /**
   * Gets the value of the 'country' field.
   * @return The value of the 'country' field.
   */
  public java.lang.String getCountry() {
    return country;
  }


  /**
   * Sets the value of the 'country' field.
   * @param value the value to set.
   */
  public void setCountry(java.lang.String value) {
    this.country = value;
  }

  /**
   * Creates a new Address RecordBuilder.
   * @return A new Address RecordBuilder
   */
  public static com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder newBuilder() {
    return new com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder();
  }

  /**
   * Creates a new Address RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Address RecordBuilder
   */
  public static com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder newBuilder(com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder other) {
    if (other == null) {
      return new com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder();
    } else {
      return new com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder(other);
    }
  }

  /**
   * Creates a new Address RecordBuilder by copying an existing Address instance.
   * @param other The existing instance to copy.
   * @return A new Address RecordBuilder
   */
  public static com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder newBuilder(com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address other) {
    if (other == null) {
      return new com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder();
    } else {
      return new com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder(other);
    }
  }

  /**
   * RecordBuilder for Address instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Address>
    implements org.apache.avro.data.RecordBuilder<Address> {

    private java.lang.String addressId;
    private int num;
    private java.lang.String street;
    private int pb;
    private java.lang.String city;
    private java.lang.String country;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.addressId)) {
        this.addressId = data().deepCopy(fields()[0].schema(), other.addressId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.num)) {
        this.num = data().deepCopy(fields()[1].schema(), other.num);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.street)) {
        this.street = data().deepCopy(fields()[2].schema(), other.street);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.pb)) {
        this.pb = data().deepCopy(fields()[3].schema(), other.pb);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.city)) {
        this.city = data().deepCopy(fields()[4].schema(), other.city);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.country)) {
        this.country = data().deepCopy(fields()[5].schema(), other.country);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
    }

    /**
     * Creates a Builder by copying an existing Address instance
     * @param other The existing instance to copy.
     */
    private Builder(com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.addressId)) {
        this.addressId = data().deepCopy(fields()[0].schema(), other.addressId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.num)) {
        this.num = data().deepCopy(fields()[1].schema(), other.num);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.street)) {
        this.street = data().deepCopy(fields()[2].schema(), other.street);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.pb)) {
        this.pb = data().deepCopy(fields()[3].schema(), other.pb);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.city)) {
        this.city = data().deepCopy(fields()[4].schema(), other.city);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.country)) {
        this.country = data().deepCopy(fields()[5].schema(), other.country);
        fieldSetFlags()[5] = true;
      }
    }

    /**
      * Gets the value of the 'addressId' field.
      * @return The value.
      */
    public java.lang.String getAddressId() {
      return addressId;
    }


    /**
      * Sets the value of the 'addressId' field.
      * @param value The value of 'addressId'.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder setAddressId(java.lang.String value) {
      validate(fields()[0], value);
      this.addressId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'addressId' field has been set.
      * @return True if the 'addressId' field has been set, false otherwise.
      */
    public boolean hasAddressId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'addressId' field.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder clearAddressId() {
      addressId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'num' field.
      * @return The value.
      */
    public int getNum() {
      return num;
    }


    /**
      * Sets the value of the 'num' field.
      * @param value The value of 'num'.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder setNum(int value) {
      validate(fields()[1], value);
      this.num = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'num' field has been set.
      * @return True if the 'num' field has been set, false otherwise.
      */
    public boolean hasNum() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'num' field.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder clearNum() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'street' field.
      * @return The value.
      */
    public java.lang.String getStreet() {
      return street;
    }


    /**
      * Sets the value of the 'street' field.
      * @param value The value of 'street'.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder setStreet(java.lang.String value) {
      validate(fields()[2], value);
      this.street = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'street' field has been set.
      * @return True if the 'street' field has been set, false otherwise.
      */
    public boolean hasStreet() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'street' field.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder clearStreet() {
      street = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'pb' field.
      * @return The value.
      */
    public int getPb() {
      return pb;
    }


    /**
      * Sets the value of the 'pb' field.
      * @param value The value of 'pb'.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder setPb(int value) {
      validate(fields()[3], value);
      this.pb = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'pb' field has been set.
      * @return True if the 'pb' field has been set, false otherwise.
      */
    public boolean hasPb() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'pb' field.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder clearPb() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'city' field.
      * @return The value.
      */
    public java.lang.String getCity() {
      return city;
    }


    /**
      * Sets the value of the 'city' field.
      * @param value The value of 'city'.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder setCity(java.lang.String value) {
      validate(fields()[4], value);
      this.city = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'city' field has been set.
      * @return True if the 'city' field has been set, false otherwise.
      */
    public boolean hasCity() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'city' field.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder clearCity() {
      city = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'country' field.
      * @return The value.
      */
    public java.lang.String getCountry() {
      return country;
    }


    /**
      * Sets the value of the 'country' field.
      * @param value The value of 'country'.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder setCountry(java.lang.String value) {
      validate(fields()[5], value);
      this.country = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'country' field has been set.
      * @return True if the 'country' field has been set, false otherwise.
      */
    public boolean hasCountry() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'country' field.
      * @return This builder.
      */
    public com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address.Builder clearCountry() {
      country = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Address build() {
      try {
        Address record = new Address();
        record.addressId = fieldSetFlags()[0] ? this.addressId : (java.lang.String) defaultValue(fields()[0]);
        record.num = fieldSetFlags()[1] ? this.num : (java.lang.Integer) defaultValue(fields()[1]);
        record.street = fieldSetFlags()[2] ? this.street : (java.lang.String) defaultValue(fields()[2]);
        record.pb = fieldSetFlags()[3] ? this.pb : (java.lang.Integer) defaultValue(fields()[3]);
        record.city = fieldSetFlags()[4] ? this.city : (java.lang.String) defaultValue(fields()[4]);
        record.country = fieldSetFlags()[5] ? this.country : (java.lang.String) defaultValue(fields()[5]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Address>
    WRITER$ = (org.apache.avro.io.DatumWriter<Address>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Address>
    READER$ = (org.apache.avro.io.DatumReader<Address>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.addressId);

    out.writeInt(this.num);

    out.writeString(this.street);

    out.writeInt(this.pb);

    out.writeString(this.city);

    out.writeString(this.country);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.addressId = in.readString();

      this.num = in.readInt();

      this.street = in.readString();

      this.pb = in.readInt();

      this.city = in.readString();

      this.country = in.readString();

    } else {
      for (int i = 0; i < 6; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.addressId = in.readString();
          break;

        case 1:
          this.num = in.readInt();
          break;

        case 2:
          this.street = in.readString();
          break;

        case 3:
          this.pb = in.readInt();
          break;

        case 4:
          this.city = in.readString();
          break;

        case 5:
          this.country = in.readString();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










