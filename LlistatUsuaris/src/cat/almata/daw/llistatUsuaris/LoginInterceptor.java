package cat.almata.daw.llistatUsuaris;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor  extends AbstractInterceptor {

        @Override
        public String intercept(ActionInvocation invocation) throws Exception {
                Map<String, Object> session = invocation.getInvocationContext().getSession();

                Usuari loginId = (Usuari) session.get("sessioUsuari");

                if (loginId == null) 
                {
                        return Action.LOGIN;
                } 
                else 
                {
                        return invocation.invoke();
                }
        }
}