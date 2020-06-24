List of changes done in HW1:
- removed web.xml, annotated servlets and added option to ignore missing web.xml to pom;
- updated TimeUTil class with toString method to format output date;
- created MealServlet to list all meals in DB;
- added getters in MealTo class in order to get them in meals.jsp;
- added initial Meal list as static variable in MealsUtil class;
- created meals.jsp - meals table is designed using CSS

Optional: 
- removed toString from TimeUtil and added javatime dependency to format date in output;
- added toString in Meal class for debugging;
- created separate file with CSS styles;
- implemented CRUD - created local storage for Meal objects. Objects are stored in a map id-Meal;
- updated formatting for JSP pages (styling, refactoring);