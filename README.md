Note: You need your own google maps API key to run this project as intended
--------------------------------------------------------------------------------------------------------------------------------------------------------------
1) Install java SE development kit 8, JDK 8
--------------------------------------------------------------------------------------------------------------------------------------------------------------
2) Install Netbeans 8.2 and install Glassfish server also during istallation. Encountered some bugs with the apache version. Netbeans 8.1 should also be fine.
--------------------------------------------------------------------------------------------------------------------------------------------------------------
3) After opening netbeans, open both the WSApplication and E-Cab
--------------------------------------------------------------------------------------------------------------------------------------------------------------
4) Go to services tab and create new database named "eCab". No need to specify username or password since this is just for testing
--------------------------------------------------------------------------------------------------------------------------------------------------------------
5) After that, A database would have been created in the name of jdbc:derby://localhost:1527/eCab [ on APP]
--------------------------------------------------------------------------------------------------------------------------------------------------------------
6) Right click that and connect, since no username or password was specified, you can just press ok
--------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------
7) Expand APP, then right click tables and execute these commands below
--------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE Customers(
	CustomerName varchar(255),
	Password varchar(255),
	PRIMARY KEY(CustomerName)
);

CREATE TABLE Drivers(
	DriverName varchar(255),
	Password varchar(255),
	VehicleNo varchar(255),
	PRIMARY KEY(DriverName)
);

CREATE TABLE AdminTable(
	AdminName varchar(255),
	Password varchar(255),
	PRIMARY KEY(AdminName)
);

CREATE TABLE Bookings(
	BookingID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START        WITH 1, INCREMENT BY 1),
	PickupAddress varchar(255),
	DateAndTime TIMESTAMP,
	DestinationAddress varchar(255),
	BookingCharge decimal(10,2),
	PRIMARY KEY(BookingID),
	CustomerName varchar(255),
	DriverName varchar(255),
	VehicleNo varchar(255),
	BookingStatus BOOLEAN,
	RejectStatus varchar(1000)
);

--------------------------------------------------------------------------------------------------------------------------------------------------------------
8)After adding Bookings table, run the commands specified below
--------------------------------------------------------------------------------------------------------------------------------------------------------------

	ALTER TABLE Bookings ADD FOREIGN KEY (CustomerName) REFERENCES customers;
	ALTER TABLE Bookings ADD FOREIGN KEY (DriverName) REFERENCES Drivers;

--------------------------------------------------------------------------------------------------------------------------------------------------------------
9)Lastly, run these two commands as well
--------------------------------------------------------------------------------------------------------------------------------------------------------------

	ALTER TABLE DRIVERS ADD ACTIVESTATUS BOOLEAN;

--------------------------------------------------------------------------------------------------------------------------------------------------------------
10) Right-click WSApplication in Projects tab and click clean and build
--------------------------------------------------------------------------------------------------------------------------------------------------------------
11) Right-click WSApplication and click deploy
--------------------------------------------------------------------------------------------------------------------------------------------------------------
11) Right-click E-Cab then clean and build
--------------------------------------------------------------------------------------------------------------------------------------------------------------
12) Right-click E-Can then run
--------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------
IF YOU COME ACROSS AN ERROR AFTER ALL THE STEPS FOLLOW THESE STEPS
--------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------
1)Configuring the IDE
--------------------------------------------------------------------------------------------------------------------------------------------------------------

Edit netbeans.conf file
	-On Windows: C:\Program Files\Netbeans\etc\netbeans.conf
	
	-On MAC: Ctrl-click /Applications/NetBeans/NetBeans IDE 6.5.app 
	 in the Finder and choose 'show package contents', then browse to 
	 /Applications/NetBeans/NetBeans\ IDE\ 6.5.app/Contents/Resources/NetBeans/etc/netbeans.conf
	
	-On Linux: /home/yourname/netbeans/etc/netbeans.conf OR
	 /opt/netbeans/etc/netbeans.conf OR
	 /usr/local/netbeans-x.0/etc/netbeans.conf

--------------------------------------------------------------------------------------------------------------------------------------------------------------
2)Configuring the GlassFish Server
--------------------------------------------------------------------------------------------------------------------------------------------------------------

Find domain.xml file
	-GLASSFISH_INSTALL/glassfish/domains/domain1/config/domain.xml

Then add the following JVM option element inside java-config
	</java-config>
  	  	...
  		<jvm-options>-Djavax.xml.accessExternalSchema=all</jvm-options>
	</java-config>

--------------------------------------------------------------------------------------------------------------------------------------------------------------
Now you should be able to run our project, error free
--------------------------------------------------------------------------------------------------------------------------------------------------------------
