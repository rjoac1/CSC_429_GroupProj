
/*
Command line code:
=======================
Compile tester:
javac -classpath classes;. DatabaseAccessorTester.java
----------------------------------------
Run the tester:
java -cp mysql-connector-java-5.1.7-bin.jar;classes;. DatabaseAccessorTester
----------------------------------------
*/
//Project imports
import models.Clerk;
import views.MainFrame;
import views.WindowPosition;


public class Main
{
    private Clerk myClerk;

    /*Main frame of the application*/
    private MainFrame mainFrame;


    /*Constructor for this class, main application object*/
    //------------------------------------------------------
    public Main(String[] args)
    {
        // Create the top-level container (main frame) and add contents to it.
        mainFrame = MainFrame.getInstance("Bike Rental System v1.0");
        try
        {
            //Test for putting locale here
            String language;
            String country;

            /*if (args.length != 2) {
                language = new String("en");
                country = new String("US");
            } else {
                language = new String(args[0]);
                country = new String(args[1]);
            }*/
            //test for french
//            language = new String("fr");
//            country = new String("FR");
            language = new String("en");
            country = new String("US");

            language = new String("en");
            country = new String("US");

            myClerk = new Clerk(language, country);
        }
        catch(Exception exc)
        {
            System.out.println("Could not create Clerk." + exc.getMessage());
        }
        mainFrame.pack();
        WindowPosition.placeCenter(mainFrame);

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception
    {
        //crank up instance of this object
        try
        {
            new Main(args);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }


		/*Scanner in2 = new Scanner(System.in);
		Scanner in = new Scanner(System.in);
		in.useDelimiter("\t\n");
		System.out.println("Enter title for book you would like to search for: ");
		String title = in.nextLine();
		BookCollection bc = new BookCollection();
		bc.findBooksWithTitleLike(title);
		bc.printData();

		System.out.println();
		System.out.println("Enter the date in which you would like to find books published before: ");
		String bookDate = in.nextLine();
		BookCollection bc2 = new BookCollection();
		bc2.findBooksOlderThanDate(bookDate);
		bc2.printData();

		System.out.println();
		System.out.println("Enter date in which you wish to find patrons younger than that date: ");
		String patronDate = in.nextLine();
		PatronCollection pc = new PatronCollection();
		pc.findPatronsYoungerThan(patronDate);
		pc.printData();

		System.out.println();
		System.out.println("Enter the zip code in which you would like to find patrons living in: ");
		String zipcode = in.nextLine();
		PatronCollection pc2 = new PatronCollection();
		pc2.findPatronsAtZipCode(zipcode);
		pc2.printData();

		System.out.println();
		System.out.println("Enter bookId and date of transaction in which to search for transaction data:");
		System.out.println("bookId: ");
		String bookId = in.nextLine();
		System.out.println("Transaction date: ");
		String transDate = in.nextLine();
		TransCollection tc = new TransCollection(bookId,null,transDate);
		tc.printData();


		System.out.println();
		System.out.println("");
		System.out.println("What would you like to insert?");
		System.out.println("1: Book");
		System.out.println("2: Patron");
		System.out.println("3: Transaction");
		System.out.println("0: Exit");
		int choice = in2.nextInt();
		if(choice==1)
		{
			Properties bookInsert = new Properties();
			System.out.println("---------------------------------");
			System.out.println("Enter book author: ");
			String input = in.nextLine();
			bookInsert.setProperty("author",input);

			System.out.println("Enter book title: ");
			input = in.nextLine();
			bookInsert.setProperty("title",input);

			System.out.println("Enter book publish year: ");
			input = in.nextLine();
			bookInsert.setProperty("pubYear",input);

			System.out.println("Enter book status: ");
			input = in.nextLine();
			bookInsert.setProperty("status",input);

			Book book = new Book(bookInsert);
			book.update();

		}
		else if(choice == 2)
		{
			Properties patronInsert = new Properties();
			System.out.println("---------------------------------");
			System.out.println("Enter patron name: ");
			String input = in.nextLine();
			patronInsert.setProperty("name",input);

			System.out.println("Enter patron address: ");
			input = in.nextLine();
			patronInsert.setProperty("address",input);

			System.out.println("Enter patron city: ");
			input = in.nextLine();
			patronInsert.setProperty("city",input);

			System.out.println("Enter state code: ");
			input = in.nextLine();
			patronInsert.setProperty("stateCode",input);

			System.out.println("Enter zip: ");
			input = in.nextLine();
			patronInsert.setProperty("zip",input);

			System.out.println("Enter email: ");
			input = in.nextLine();
			patronInsert.setProperty("email",input);

			System.out.println("Enter date of birth: ");
			input = in.nextLine();
			patronInsert.setProperty("dateOfBirth",input);

			System.out.println("Enter status: ");
			input = in.nextLine();
			patronInsert.setProperty("status",input);

			Patron patron = new Patron(patronInsert);
			patron.update();
		}
		else if (choice == 3)
		{
			Properties transactionInsert = new Properties();
			System.out.println("---------------------------------");
			System.out.println("Enter transaction ID: ");
			String input = in.nextLine();
			transactionInsert.setProperty("transId",input);

			System.out.println("Enter book ID: ");
			input = in.nextLine();
			transactionInsert.setProperty("bookId",input);

			System.out.println("Enter patron ID: ");
			input = in.nextLine();
			transactionInsert.setProperty("patronId",input);

			System.out.println("Enter transaction type: ");
			input = in.nextLine();
			transactionInsert.setProperty("transType",input);

			System.out.println("Enter date of transaction: ");
			input = in.nextLine();
			transactionInsert.setProperty("dateOfTrans",input);

			Trans trans = new Trans(transactionInsert);
			trans.update();
		}
		else
		{
			System.exit(0);
		}
		//Book b = new Book(new String("1"));
		//b.printData();*/

    }

}

