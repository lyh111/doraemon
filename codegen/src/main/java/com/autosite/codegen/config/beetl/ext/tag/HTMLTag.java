package com.autosite.codegen.config.beetl.ext.tag;

import com.autosite.common.collect.ListUtils;
import com.autosite.common.collect.MapUtils;
import com.autosite.common.lang.StringUtils;
import org.beetl.core.BodyContent;
import org.beetl.core.Context;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.statement.Statement;
import org.beetl.core.tag.HTMLTagSupportWrapper;
import org.beetl.core.tag.Tag;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class HTMLTag extends HTMLTagSupportWrapper {
    private HttpServletRequest request;
    private String tagName;
    private LinkedList<HTMLTag> tags;

    public Map<String, Object> getAttrs() {
        return (Map)(this.args.length >= 2 ? (Map)this.args[1] : MapUtils.newHashMap());
    }

    public HTMLTag getParentByTagName(String tagName) {
        int a;
        for(int i = a = this.tags.size() - 1; i >= 0; i = a) {
            HTMLTag tag;
            if ((tag = (HTMLTag)this.tags.get(a)).tagName != null && tag.tagName.equals(tagName)) {
                return tag;
            }
            --a;
        }

        return null;
    }

    public HTMLTag getParent() {
        HTMLTag tag = null;

        Iterator var2;
        for(Iterator itr = var2 = this.tags.iterator(); itr.hasNext(); itr = var2) {
            HTMLTag _tag;
            if ((_tag = (HTMLTag)var2.next()) == this) {
                return tag;
            }

            tag = _tag;
        }

        return null;
    }

    protected void callHtmlTag(String path) {
        this.tagName = StringUtils.substringAfterLast((String)this.args[0], ":");
        Template tmp = this.gt.getTemplate(path,ctx);

        tmp.binding(this.ctx.globalVar);

        Iterator itr;
        for(Iterator _itr = itr = this.getAttrs().entrySet().iterator(); _itr.hasNext(); _itr = itr) {
            Map.Entry entry = (Map.Entry)itr.next();
            tmp.binding((String)entry.getKey(), entry.getValue());
        }

        tmp.binding("thisTag", this);
        BodyContent content = super.getBodyContent();
        tmp.binding("tagBody", content);
        tmp.renderTo(this.ctx.byteWriter);
    }

    public void render() {
        this.tags = (LinkedList)this.request.getAttribute("__htmlTags");
        if (this.tags == null) {
            this.tags = ListUtils.newLinkedList();
            this.request.setAttribute("__htmlTags", this.tags);
        }

        this.tags.add(this);
        super.render();
    }

    public HTMLTag() {
    }

    public void init(Context ctx, Object[] args, Statement st) {
        super.init(ctx, args, st);
        this.request = (HttpServletRequest)this.ctx.getGlobal("request");
    }
}
