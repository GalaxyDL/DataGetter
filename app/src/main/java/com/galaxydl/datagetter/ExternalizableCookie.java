package com.galaxydl.datagetter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import okhttp3.Cookie;

/**
 * Created by Galaxy on 2018/3/15.
 */

final class ExternalizableCookie implements Externalizable {
    public static final long serialVersionUID = 2114151658939974797L;

    private transient Cookie cookie;

    public ExternalizableCookie() {

    }

    public ExternalizableCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        return cookie;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(cookie.name());
        out.writeObject(cookie.value());
        out.writeLong(cookie.expiresAt());
        out.writeObject(cookie.path());
        out.writeBoolean(cookie.secure());
        out.writeBoolean(cookie.httpOnly());
        out.writeBoolean(cookie.hostOnly());
        out.writeObject(cookie.domain());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Cookie.Builder builder = new Cookie.Builder()
                .name((String) in.readObject())
                .value((String) in.readObject())
                .expiresAt(in.readLong())
                .path((String) in.readObject());
        if (in.readBoolean()) {
            builder.secure();
        }
        if (in.readBoolean()) {
            builder.httpOnly();
        }
        if (in.readBoolean()) {
            builder.hostOnlyDomain((String) in.readObject());
        } else {
            builder.domain((String) in.readObject());
        }
        cookie = builder.build();
    }
}
