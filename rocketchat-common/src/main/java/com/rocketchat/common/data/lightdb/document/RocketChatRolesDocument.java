package com.rocketchat.common.data.lightdb.document;

import org.json.JSONObject;

import java.util.Date;

public class RocketChatRolesDocument {
    String name;
    String scope;
    String description;
    Boolean _protected;
    Date updatedAt;

    RocketChatRolesDocument (JSONObject roles){
        name = roles.optString("name");
        scope = roles.optString("scope");
        description = roles.optString("description");
        _protected = roles.optBoolean("protected");
        if (roles.opt("_updatedAt") != null) {
            updatedAt = new Date(roles.optJSONObject("_updatedAt").optLong("$date"));
        }
    }

    public String getName() {
        return name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean get_protected() {
        return _protected;
    }

    public void set_protected(Boolean _protected) {
        this._protected = _protected;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
