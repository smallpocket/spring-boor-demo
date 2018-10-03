package example.shiro.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 由于我们编写的是无状态的，每人情况是会创建session对象的，
 * 那么我们需要修改createSubject关闭session的创建。
 *
 * @Time : Created by Hyper on 2018/10/3 16:51
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建session.
        context.setSessionCreationEnabled(false);
        System.out.println("shiro.config.subjectFactory.createSubject.SessionCreationEnabled.false");
        return super.createSubject(context);
    }
}
