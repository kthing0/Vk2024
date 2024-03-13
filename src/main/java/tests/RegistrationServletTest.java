package tests;

import servlets.RegistrationServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegistrationServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private RegistrationServlet registrationServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        registrationServlet = new RegistrationServlet();
    }

    @Test
    public void testSuccessfulRegistration() throws Exception {
        when(request.getParameter("username")).thenReturn("user1");
        when(request.getParameter("password")).thenReturn("password1");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        registrationServlet.doPost(request, response);

        writer.flush();
        assertEquals("{\"message\": \"User registered successfully\"}", stringWriter.toString().trim());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

}

