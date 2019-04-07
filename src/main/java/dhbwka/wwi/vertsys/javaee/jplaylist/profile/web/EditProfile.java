// Servlet für die Benutzerverwaltung
// Frägt den Nutzer ab
// Füllt die Felder auf dem JSP und speichert im Nachgang den inhalt der Felder über die Methoden der User-Klasse und den UserBean in die Userdatenbank
// sind die Felder mit ungültigen EIngaben gefüllt wird eine Errorliste angelegt, die dann dem Nutzer inform einer Aufzählung ausgegeben wird
package dhbwka.wwi.vertsys.javaee.jplaylist.profile.web;

import dhbwka.wwi.vertsys.javaee.jplaylist.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.jplaylist.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.jplaylist.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.jplaylist.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.jplaylist.common.web.WebUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/app/editprofile")
public class EditProfile extends HttpServlet {

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User user = this.userBean.getCurrentUser();


        if (session.getAttribute("user_form") == null) {
            session.setAttribute("user_form", this.createUserForm(user));
        }


        //call jsp
        request.getRequestDispatcher("/WEB-INF/profile/viewprofile.jsp").forward(request, response);
        session.removeAttribute("user_form");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        List<String> errors = new ArrayList<>();
        User user = this.userBean.getCurrentUser();

        String vorname = request.getParameter("vorname");
        String nachname = request.getParameter("nachname");
        String passwordOld = request.getParameter("old_password");
        String password1 = request.getParameter("new_password");
        String password2 = request.getParameter("validate_password");

       
        user.setVorname(vorname);
        user.setNachname(nachname);

        validationBean.validate(user, errors);

        if(!password1.isEmpty() && !password2.isEmpty())
        try {
            if(password1.equals(password2)){
                 userBean.changePassword(user, passwordOld, password2);
            }else{
                errors.add("Passwords don't match");
            }
        } catch (UserBean.InvalidCredentialsException ex) {
            errors.add("Password could not be changed");
        }
        
        if(errors.isEmpty()){
            userBean.update(user);
        }
        
        if(errors.isEmpty()){
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
           //response.sendRedirect(request.getRequestURI()); 
        }else{
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            HttpSession session = request.getSession();
            session.setAttribute("user_form", formValues);
            String url = request.getRequestURI();
            response.sendRedirect(url);
        }
        
        

        
    }

    public FormValues createUserForm(User user) {
        Map<String, String[]> values = new HashMap<String, String[]>();

        values.put("vorname", new String[]{
            user.getVorname()
        });

        values.put("nachname", new String[]{
            user.getNachname()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);

        return formValues;
    }

    

}
