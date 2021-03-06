package uo.asw.agents.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import Agents.uo.asw.Application;
import Agents.uo.asw.agents.util.CSVLoader;
import Agents.uo.asw.agents.util.Check;
import Agents.uo.asw.agents.util.DateUtil;
import Agents.uo.asw.agents.util.LoaderMin;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UtilTest {

	@Autowired
	private CSVLoader csvLoader;
	
	private static String[] validEmails, invalidEmails;
	private LoaderMin loaderMin;
	
	 /**
     * Carga de datos
     */
    @BeforeClass
    public static void emailProviderText() {
        validEmails = new String[] { "test@example.com",
                "test-101@example.com", "test.101@yahoo.com",
                "test101@example.com", "test_101@example.com",
                "test-101@test.net", "test.100@example.com.au", "test@e.com",
                "test@1.com", "test@example.com.com", "test+101@example.com",
                "101@example.com", "test-101@example-test.com" };
 
        invalidEmails = new String[] { "example", "example@.com.com",
                "exampel101@test.a", "exampel101@.com", ".example@test.com",
                "example**()@test.com", "example@%*.com",
                "example..101@test.com", "example.@test.com",
                "test@example_101.com", "example@test@test.com",
                "example@test.com.a5" };
    }
	
	
    @Before
    public void setUp() throws Exception {
    	loaderMin = new LoaderMin("nombre", "localizacion","usuario@gmail.com","1" ,"kind","kindCode");
    }

	@Test
	public void newCitizenMinTest() {
		
		String id = "2";
		String nombre = "fernando";
//		String apellidos = "sanchez";
//		Date fechaNacimiento = new Date();
//		int edad = DateUtil.getYears(fechaNacimiento);
		String email = "fernando@gmail.com";

		loaderMin.setId(id);
		loaderMin.setName(nombre);
//		loaderMin.setLastName(apellidos);
//		loaderMin.setEdad(edad);
		loaderMin.setEmail(email);

	
		assertEquals(id, loaderMin.getId());
		assertEquals(nombre, loaderMin.getName());
//		assertEquals(apellidos, loaderMin.getLastName());
//		assertEquals(edad, loaderMin.getEdad());
		assertEquals(email, loaderMin.getEmail());
		
	}
	
	@Test
	public void loaderMinTest() {
		LoaderMin lm1 = new LoaderMin("nombre1", "localizacion1",  "email1@prueba.es",
				"id1", "person", "1");
		assertEquals("nombre1", lm1.getName());
		assertEquals("localizacion1", lm1.getLocation());
		assertEquals("email1@prueba.es", lm1.getEmail());
		assertEquals("id1", lm1.getId());
		assertEquals("person", lm1.getKind());
		assertEquals("1", lm1.getKindCode());
		
		LoaderMin lm2 = lm1;
		assertTrue(lm1.equals(lm2));
		String s1 = lm1.toString();
		String s2 = lm2.toString();
		assertTrue(s1.equals(s2));
		
	}
	
	
	@Test
	public void dateUtilTest() throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		
		//Ya ha cumplido años
		String dateString1 = "10-02-1990";
		Date date1 = formatter.parse(dateString1);
		int edad1 = DateUtil.getYears(date1);
	    int d1 = Integer.parseInt(formatter.format(date1));
	    int d2 = Integer.parseInt(formatter.format(Calendar.getInstance().getTime()));
	    int age = (d2-d1)/10000;
		assertEquals(age, edad1);

		//Aun no ha cumplido años
		String dateString2 = "10-08-1990";
		Date date2 = formatter.parse(dateString2);
		int edad2 = DateUtil.getYears(date2);
	    int d3 = Integer.parseInt(formatter.format(date2));
	    int d4 = Integer.parseInt(formatter.format(Calendar.getInstance().getTime()));
	    int age2 = (d4-d3)/10000;
		assertEquals(age2, edad2);
	}
	
	@Test
	public void checkTest(){
		
		assertTrue(Check.validateEmail("juan@gmail.com"));
		assertTrue(Check.validateEmail("juan@uniovi.es"));
		
		assertFalse(Check.validateEmail("usuario"));
		assertFalse(Check.validateEmail("usuario@gmail"));
		assertFalse(Check.validateEmail("usuario.com"));

	}
	
	
	 
 
    /**
     * Test para email valido
     */
    @Test
    public void validEmailTest() {
 
        for (String temp : validEmails) {
 
            assertTrue(Check.validateEmail(temp));
        }
 
    }
 
    /**
     * Test para email no valido
     */
    @Test
    public void invalidEmailTest() {
 
        for (String temp : invalidEmails) {
 
          assertFalse(Check.validateEmail(temp));
        }
 
    }
    
    /**
     * CSVLoader
     * @throws IOException 
     */
    
    @Test
    public void getKeyCodesNoFicheroTest() {
    	try {
			csvLoader.getKeyCodes("no_ruta");
			fail ("Se esperaba excepcion");
		} catch (IOException e) {}
    	
    }
    
    
    @Test
    public void getRutaPorDefectoTest() {
    	assertEquals("src/main/resources/db/csv/kindCode.csv", csvLoader.getRutaPorDefecto());
    }
    
    
}
