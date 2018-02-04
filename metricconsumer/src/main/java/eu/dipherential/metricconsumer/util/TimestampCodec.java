/*******************************************************************************
 * Copyright 2018 Ismail Bayraktar
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eu.dipherential.metricconsumer.util;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ParseUtils;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.exceptions.InvalidTypeException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.ParseException;

public class TimestampCodec extends TypeCodec<Timestamp> {

    TimestampCodec() {
        super(DataType.timestamp(), Timestamp.class);
    }

    @Override
    public ByteBuffer serialize(Timestamp value, ProtocolVersion protocolVersion) {
        return value == null ? null : bigint().serializeNoBoxing(value.getTime(), protocolVersion);
    }

    @Override
    public Timestamp deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) {
        if (bytes == null || bytes.remaining() == 0) {
            return null;
        }
        long millis = bigint().deserializeNoBoxing(bytes, protocolVersion);
        return new Timestamp(millis);
    }

    @Override
    public String format(Timestamp value) {
        if (value == null) {
            return "NULL";
        }
        return Long.toString(value.getTime());
    }

    @Override
    public Timestamp parse(String value) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("NULL")) {
            return null;
        }
        // strip enclosing single quotes, if any
        if (ParseUtils.isQuoted(value)) {
            value = ParseUtils.unquote(value);
        }

        if (ParseUtils.isLongLiteral(value)) {
            try {
                return new Timestamp(Long.parseLong(value));
            } catch (NumberFormatException e) {
                throw new InvalidTypeException(String.format("Cannot parse timestamp value from \"%s\"", value));
            }
        }

        try {
            return new Timestamp(ParseUtils.parseDate(value).getTime());
        } catch (ParseException e) {
            throw new InvalidTypeException(String.format("Cannot parse timestamp value from \"%s\"", value));
        }
    }
}
