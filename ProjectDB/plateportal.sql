create database PlatePortal

use PlatePortal

create table City (
	CityID int primary key identity,
	CityName nvarchar(50) not null
)

create table District (
	DistrictID int primary key identity,
	CityID int references City(CityID) not null,
	DistrictName nvarchar(50) not null
)

create table Admin (
	AdminUsername nvarchar(50) primary key,
	AdminPassword char(64) not null
)

create table CatererRank (
	RankID int primary key identity,
	RankFee float not null check(RankFee > 0),
	RankCPO float not null check(RankCPO >= 0),
	RankMaxDish int not null check(RankMaxDish > 0)
)

create table Caterer (
	CatererEmail nvarchar(50) primary key,
	Password char(64) not null,
	RankID int references CatererRank(RankID) not null,
	CatererRating float,
	RankStartDate date not null,
	RankEndDate date not null,
	check(RankEndDate > RankStartDate),
	PaymentInformation nvarchar(50) not null,
	Point int default 0 check(Point >= 0),
	Description nvarchar(200),
	Active int not null default 1 check(Active in (0, 1)),
	ProfileImage nvarchar(100),
	FullName nvarchar(50) not null,
	Phone nvarchar(20) not null,
	Gender int not null check(Gender in (0, 1)),
	Address nvarchar(200) not null,
	DistrictID int references District(DistrictID) not null,
	Birthday date,
	CreateDate date not null
)

create table Customer (
	CustomerEmail nvarchar(50) primary key,
	Password char(64) not null,
	Point int default 0 check(Point >= 0),
	RollChance int default 0 check(RollChance >= 0),
	Active int not null default 1 check(Active in (0, 1)),
	ProfileImage nvarchar(100),
	FullName nvarchar(50) not null,
	Phone nvarchar(20) not null,
	Gender int not null check(Gender in (0, 1)),
	Address nvarchar(200) not null,
	DistrictID int references District(DistrictID) not null,
	Birthday date,
	CreateDate date not null
)

create table FavoriteCaterer (
	CatererEmail nvarchar(50) references Caterer(CatererEmail) not null,
	CustomerEmail nvarchar(50) references Customer(CustomerEmail) not null,
	primary key(CatererEmail, CustomerEmail)
)

create table BannerType (
	TypeID int primary key identity,
	TypePrice float not null check(TypePrice > 0),
	TypeDescription nvarchar(150)
)

create table Banner (
	BannerID int primary key identity,
	TypeID int references BannerType(TypeID) not null,
	CatererEmail nvarchar(50) references Caterer(CatererEmail) not null,
	BannerImage nvarchar(100) not null,
	BannerStartDate date not null,
	BannerEndDate date not null,
	check(BannerEndDate >= BannerStartDate)
)

create table VoucherType (
	TypeID int primary key identity,
	TypeDescription nvarchar(150)
)

create table Voucher (
	VoucherID int primary key identity,
	TypeID int references VoucherType(TypeID) not null,
	CatererEmail nvarchar(50) references Caterer(CatererEmail) not null,
	StartDate date not null,
	EndDate date not null,
	check(EndDate >= StartDate),
	VoucherValue float not null check(VoucherValue > 0 ),
	MaxValue float check(MaxValue >= 0)
)

create table Feedback (
	FeedbackID int primary key identity,
	Email nvarchar(50) not null,
	FeedbackDetails nvarchar(200) not null,
	FeedbackDate date not null
)

create table CateringOrder (
	OrderID int primary key identity,
	CatererEmail nvarchar(50) references Caterer(CatererEmail) not null,
	CustomerEmail nvarchar(50) references Customer(CustomerEmail) not null,
	VoucherID int references Voucher(VoucherID),
	OrderAddress nvarchar(200) not null,
	DistrictID int references District(DistrictID) not null,
	OrderTime datetime not null,
	CreateDate date not null,
	OrderState nvarchar(20) not null check(OrderState in ('Waiting', 'Accepted', 'Waiting confirm', 'Paid', 'Cancelled', 'Finished')),
	PointDiscount int check(PointDiscount >= 0),
	VoucherDiscount float check(VoucherDiscount >= 0),
	Value float not null check(Value >= 0),
	Note nvarchar(200)
)

create table CatererRating (
	RatingID int primary key identity,
	OrderID int references CateringOrder(OrderID) not null,
	CatererEmail nvarchar(50) references Caterer(CatererEmail) not null,
	Rate float not null check(Rate between 0 and 5),
	Comment nvarchar(200)
)

create table DeliveryAddress (
	AddressID int primary key identity,
	CustomerEmail nvarchar(50) references Customer(CustomerEmail) not null,
	DistrictID int references District(DistrictID) not null,
	Address nvarchar(200) not null,
)

create table Dish (
	DishID int primary key identity,
	CatererEmail nvarchar(50) references Caterer(CatererEmail) not null,
	DishName nvarchar(50) not null,
	DishImage nvarchar(100) not null,
	DishDescription nvarchar(200),
	DishPrice float not null check(DishPrice > 0),
	DishStatus int default 1 not null check(DishStatus in (0, 1))
)

create table OrderDetails (
	DishID int references Dish(DishID) not null,
	OrderID int references CateringOrder(OrderID) not null,
	primary key(DishID, OrderID),
	Quantity int not null check(Quantity > 0),
	Price float not null check(Price > 0)
)

create table MinigameReward (
	RewardID int primary key identity,
	Point int not null check(Point >= 0),
	Weight int not null check(Weight > 0)
)

create table Report (
	ReportID int primary key identity,
	Reporter nvarchar(50) not null,
	Reportee nvarchar(50) not null,
	ReportDescription nvarchar(200) not null,
	ReportDate date not null,
	ReportStatus int default 0 not null check(ReportStatus in (0, 1))
)

create table Notification (
	NotificationID int primary key identity,
	Sender nvarchar(50) not null,
	Receiver nvarchar(50) not null,
	NotificationContents nvarchar(200) not null,
	NotificationTime datetime not null
)

create table PaymentType (
	TypeID int primary key identity,
	Description nvarchar(100) not null
)

create table PaymentHistory (
	PaymentID int primary key identity,
	CatererEmail nvarchar(50) references Caterer(CatererEmail) not null,
	TypeID int references PaymentType(TypeID) not null,
	Value float not null check(Value >= 0),
	PaymentTime datetime not null,
	Description nvarchar(200) not null
)

insert into City values
	('Can Tho'),
	('Da Nang'),
	('Hai Phong'),
	('Ha Noi'),
	('Ho Chi Minh City')

select * from City

insert into District values
	(1, 'Cai Rang'),
	(1, 'Ninh Kieu'),
	(1, 'Binh Thuy'),
	(1, 'O Mon'),
	(1, 'Thot Not'),
	(2, 'Thanh Khe'),
	(2, 'Hai Chau'),
	(2, 'Cam Le'),
	(2, 'Hoa Vang'),
	(2, 'Lien Chieu'),
	(2, 'Ngu Hanh Son'),
	(2, 'Son Tra'),
	(3, 'Le Chan'),
	(3, 'Ngo Quyen'),
	(3, 'Hong Bang'),
	(3, 'Duong Kinh'),
	(3, 'Do Son'),
	(3, 'Hai An'),
	(3, 'Kien An'),
	(4, 'Dong Da'),
	(4, 'Thanh Xuan'),
	(4, 'Hai Ba Trung'),
	(4, 'Ba Dinh'),
	(4, 'Bac Tu Liem'),
	(4, 'Cau Giay'),
	(4, 'Ha Dong'),
	(4, 'Hoan Kiem'),
	(4, 'Hoang Mai'),
	(4, 'Long Bien'),
	(4, 'Nam Tu Liem'),
	(4, 'Tay Ho'),
	(5, 'District 1'),
	(5, 'District 3'),
	(5, 'District 4'),
	(5, 'District 5'),
	(5, 'District 6'),
	(5, 'District 7'),
	(5, 'District 8'),
	(5, 'District 10'),
	(5, 'District 11'),
	(5, 'District 12'),
	(5, 'Go Vap'),
	(5, 'Tan Binh'),
	(5, 'Tan Phu'),
	(5, 'Binh Thanh'),
	(5, 'Phu Nhuan'),
	(5, 'Binh Tan')

select * from District

insert into Admin values
	('plateportal@gmail.com', '70da09dc10089b114d7863f0bf9a5aa9cf5a6bdb5c92e35352038f131753daea') --plateportaladmin

select * from Admin

insert into CatererRank values
	(15, 2, 30),
	(30, 1, 50),
	(100, 0, 100)

select * from CatererRank

insert into Caterer values
	('caterer1@gmail.com', '0548d93e3be91c58636de346fb1a5cb69ec00f42738bbf9f68ce01e2551871d6', 1, 4.5, '2024-05-17', '2024-07-17', 'NCB 0123456798', 0, 'Experienced caterer specializing in Italian cuisine.', 1, 'caterer1.jpg', 'Golden Hands', '0987654321', 0, '123 Nguyen Van Linh, Tan Thuan Tay', 37, '1988-09-20', '2022-02-10') /*caterer1*/,
	('caterer2@gmail.com', 'f946b4a6171db328536ed75e8d0df76ef811f881f50202ae4cdfe7a03f719630', 2, 4.2, '2024-05-01', '2024-05-31', 'NCB 9461510981', 0, 'Passionate about fusion dishes.', 0, 'caterer2.jpg', 'Something Snacky!', '0123456789', 1, '456 Le Loi, Hoang Van Thu', 15, '1990-05-15', '2022-01-01') /*caterer2*/,
	('caterer3@gmail.com', 'dc82abb10430b63a5517cfe1d49b2226e70835406810010a3ff3f6600345ed72', 3, 3, '2024-05-01', '2024-07-01', 'NCB 0316521654', 0, 'Upscale catering with a focus on fresh ingredients.', 1, 'caterer3.jpg', 'The Fine Day', '0156418944', 0, '456 Le Loi, Hoang Van Thu', 37, '1990-05-15', '2022-01-01') /*caterer3*/,
	('caterer4@gmail.com', 'a11232d136da12cc1753c1f2d310ba17859ad5025cd8406bc51a68e03cf29ddc', 1, 2.2, '2024-04-01', '2024-05-01', 'NCB 5640164162', 0, 'Offers budget-friendly catering options.', 1, 'caterer4.jpg', 'Happy Chew', '0468531953', 1, '10 Nguyen Trai, An Binh', 26, '1990-05-15', '2022-01-01') /*caterer4*/,
	('caterer5@gmail.com', 'e4bd2fdcd90711c1ca3ac2f1abf1bcdd761f39d149870f49c0dc588a1d87f4c6', 1, 1.8, '2024-05-01', '2024-07-01', 'NCB 4641618612', 0, 'Offers catering for corporate events and meetings.', 1, 'caterer5.jpg', 'Royal', '0563249856', 0, '36 Le Lai, Anh Dung', 6, '1990-05-15', '2022-01-01') /*caterer5*/

select * from Caterer

insert into Customer values
	('ndhunga22008@cusc.ctu.edu.vn', '912b3fd2cf096a121c4815f00075dc870ea2ed9138a6d6c2f409270625000023', 0, 0, 1, 'customer0.jpg', 'Duc Hung Nguyen', '0123456789', 0, '1 Ly Tu Trong, An Phu', 2, '2004-03-25', '2020-05-16') /*customer0*/,
	('customer1@gmail.com', 'dea26157fa355301663174eac368538cff8939f36681d6712dedba439ab98b70', 0, 0, 1, 'customer1.jpg', 'Duc Anh Tran', '0756448651', 0, '145 Ly Loi, An Dinh', 6, '2004-09-12', '2020-04-12') /*customer1*/,
	('customer2@gmail.com', 'c8c7cb5b9e8f7a1b3d1d02602ada62327132391dbe0e8ee07913cd550eea1f3b', 0, 0, 0, 'customer2.jpg', 'Thi Anh Ly', '0751368456', 1, '39 Vong Cung, An Binh', 2, '2004-09-15', '2020-04-17') /*customer2*/,
	('customer3@gmail.com', '18c5c9be898c65c5e5c51ac3e94feacff0b991f8463a3a18eb524e9f7e6131a8', 0, 0, 1, 'customer3.jpg', 'Binh Chi Lam', '0893656481', 0, '45 Nguyen Trai, An Hiep', 9, '2004-10-01', '2021-09-12') /*customer3*/,
	('customer4@gmail.com', '059550e3991d13d8d6f4f0e980c67138a367e34b0e189be682f8b660de681eca', 0, 0, 1, 'customer4.jpg', 'Duong Vo Thi', '0592344935', 1, '12 Hong Bang, Vo Thi Sau', 2, '2004-12-12', '2022-03-02') /*customer4*/

select * from Customer

insert into FavoriteCaterer values
	('caterer1@gmail.com', 'customer1@gmail.com'),
	('caterer3@gmail.com', 'customer1@gmail.com'),
	('caterer4@gmail.com', 'customer1@gmail.com'),
	('caterer2@gmail.com', 'customer4@gmail.com'),
	('caterer5@gmail.com', 'customer3@gmail.com')

select * from FavoriteCaterer

insert into BannerType values
	(100, 'On top of the main page.'),
	(50, 'On the left of the main page.'),
	(50, 'On the right of the main page.'),
	(40, 'At the bottom of the main page.')

select * from BannerType

insert into Banner values
	(1, 'caterer1@gmail.com', 'banner1.jpg', '2024-05-01', '2024-05-16'),
	(2, 'caterer2@gmail.com', 'banner2.jpg', '2024-05-15', '2024-05-30'),
	(3, 'caterer3@gmail.com', 'banner3.jpg', '2024-05-20', '2024-06-04'),
	(1, 'caterer4@gmail.com', 'banner4.jpg', '2024-05-20', '2024-06-04'),
	(2, 'caterer5@gmail.com', 'banner5.jpg', '2024-05-01', '2024-05-16'),
	(4, 'caterer1@gmail.com', 'banner6.jpg', '2024-05-01', '2024-05-16'),
	(1, 'caterer2@gmail.com', 'banner7.jpg', '2024-05-10', '2024-05-25'),
	(2, 'caterer3@gmail.com', 'banner8.jpg', '2024-05-15', '2024-05-30'),
	(3, 'caterer5@gmail.com', 'banner9.jpg', '2024-05-20', '2024-06-04'),
	(1, 'caterer5@gmail.com', 'banner10.jpg', '2024-05-25', '2024-06-09')

select * from Banner

insert into VoucherType values
	('Fixed amount vouchers with a set value deducted from the total bill.'),
	('Discount vouchers with a percentage discount applied to the total bill.')

select * from VoucherType

insert into Voucher values
	(1, 'caterer1@gmail.com', '2024-06-01', '2024-06-30', 10, 10),
	(1, 'caterer2@gmail.com', '2024-07-15', '2024-08-10', 20, 20),
	(1, 'caterer3@gmail.com', '2024-05-20', '2024-06-15', 15, 15),
	(2, 'caterer4@gmail.com', '2024-08-20', '2024-09-15', 5.00, 30),
	(2, 'caterer5@gmail.com', '2024-04-01', '2024-04-30', 2.50, 40),
	(1, 'caterer1@gmail.com', '2024-09-01', '2024-09-30', 25.00, 25),
	(2, 'caterer2@gmail.com', '2024-10-10', '2024-11-09', 10.00, 20),
	(2, 'caterer3@gmail.com', '2024-11-15', '2024-12-15', 7.50, 15),
	(1, 'caterer4@gmail.com', '2024-12-20', '2025-01-20', 12, 12),
	(1, 'caterer5@gmail.com', '2025-01-25', '2025-02-22', 18, 18)

select * from Voucher

insert into Feedback values
	('caterer1@gmail.com', 'The website is easy to navigate and the products are well-organized.', '2024-05-17'),
	('customer1@gmail.com', 'I love the variety of products and the fast shipping.', '2024-05-16'),
	('customer2@gmail.com', 'The customer service is excellent and they were very helpful with my order.', '2024-05-15'),
	('customer5@gmail.com', 'The prices are competitive and the quality of the products is great.', '2024-05-14'),
	('caterer3@gmail.com', 'I would highly recommend this website to anyone looking for high-quality products and excellent service.', '2024-05-13')

select * from Feedback

insert into CateringOrder values
	('caterer1@gmail.com', 'customer1@gmail.com', 1, '35 Nguyen Trai', 1, '2024-06-30 11:00:00', '2024-06-01', 'Waiting', 0, 10, 1800, null),
	('caterer1@gmail.com', 'customer3@gmail.com', null, '76 Nguyen Van Cu', 20, '2024-06-26 15:00:00', '2024-05-15', 'Paid', 0, 0, 440, 'Fast reply pls'),
	('caterer1@gmail.com', 'customer1@gmail.com', null, '45 Le Loi', 12, '2024-06-22 18:00:00', '2024-05-15', 'Accepted', 0, 0, 500, null),
	('caterer2@gmail.com', 'customer4@gmail.com', null, '89 Le Lai', 42, '2024-03-20 08:00:00', '2024-03-10', 'Finished', 0, 0, 360, null),
	('caterer3@gmail.com', 'customer3@gmail.com', null, '74 Vo Thi Sau', 17, '2024-03-15 08:00:00', '2024-02-25', 'Finished', 0, 0, 300, null),
	('caterer4@gmail.com', 'customer1@gmail.com', null, '77 Vo Nguyen Giap', 26, '2024-03-18 13:00:00', '2024-02-25', 'Finished', 0, 0, 360, null),
	('caterer5@gmail.com', 'customer1@gmail.com', null, '46 A1', 32, '2024-03-19 14:00:00', '2024-02-25', 'Finished', 0, 0, 120, 'Be careful'),
	('caterer1@gmail.com', 'customer1@gmail.com', null, '46 B2', 32, '2024-03-19 17:00:00', '2024-02-25', 'Finished', 0, 0, 200, null)

select * from CateringOrder

insert into CatererRating values
	(8, 'caterer1@gmail.com', 4.5, 'Excellent food and service!'),
	(4, 'caterer2@gmail.com', 4.2, null),
	(5, 'caterer3@gmail.com', 3, 'Great value for the price. The food arrived on time and was still warm.'),
	(6, 'caterer4@gmail.com', 2.2, null),
	(7, 'caterer5@gmail.com', 1.8, 'Food was cold and arrived incomplete. Disappointed with the service.')

select * from CatererRating

insert into DeliveryAddress values
	('customer1@gmail.com', 1, '123 Pho Chinh, A'),
	('customer2@gmail.com', 2, '456 Nguyen Trai, B'),
	('customer3@gmail.com', 3, '789 Le Loi, C'),
	('customer4@gmail.com', 4, '1001 Tran Hung Dao, D'),
	('customer2@gmail.com', 1, '123 Pho Chinh, A'),
	('customer1@gmail.com', 5, '222 Hanh Phuc, E'),
	('customer3@gmail.com', 6, '333 Anh Duong, toa nha A, F'),
	('customer4@gmail.com', 7, '444 Nui Trinh, G'),
	('customer3@gmail.com', 2, '567 Nguyen Trai, B'),
	('customer2@gmail.com', 3, '890 Le Loi, C')

select * from DeliveryAddress

insert into Dish values
	('caterer1@gmail.com', 'Bun Bo Hue', 'bunbohue.jpg', 'Bun Bo Hue is a spicy noodle soup from Hue, Vietnam.', 100, 1),
	('caterer1@gmail.com', 'Pho Bo', 'phobo.jpg', 'Pho Bo is a Vietnamese beef noodle soup with a rich broth.', 120, 1),
	('caterer1@gmail.com', 'Goi Cuon', 'goicuon.jpg', 'Goi Cuon are fresh spring rolls filled with vegetables, meat, and shrimp.', 80, 1),
	('caterer1@gmail.com', 'Banh Mi', 'banhmi.jpg', 'Banh Mi is a Vietnamese baguette filled with savory ingredients.', 50, 1),
	('caterer1@gmail.com', 'Nem Nuong', 'nemnuong.jpg', 'Nem Nuong are grilled pork skewers marinated in spices.', 70, 1),
	('caterer1@gmail.com', 'Banh Xeo', 'banhxeo.jpg', 'Banh Xeo is a crispy crepe filled with savory fillings.', 60, 1),
	('caterer1@gmail.com', 'Che', 'che.jpg', 'Che is a Vietnamese dessert made with fruits, beans, and jellies.', 40, 1),
	('caterer1@gmail.com', 'Cafe Phin', 'cafephin.jpg', 'Cafe Phin is Vietnamese strong coffee brewed with a filter.', 25, 1),
	('caterer1@gmail.com', 'Tra Da', 'trada.jpg', 'Tra Da is Vietnamese iced tea, a refreshing drink.', 20, 1),
	('caterer1@gmail.com', 'Nuoc Cam', 'nuocmia.jpg', 'Nuoc Cam is fresh sugarcane juice, a healthy and delicious drink.', 30, 1),
	('caterer1@gmail.com', 'Pho Ga', 'phoga.jpg', 'Chicken noodle soup with a rich and flavorful broth.', 100, 1),
	('caterer1@gmail.com', 'Bun Rieu Cua', 'bunrieucua.jpg', 'Tomato-based noodle soup with crab patties, vermicelli noodles, and herbs.', 80, 1),
	('caterer1@gmail.com', 'Banh Beo', 'banhbeo.jpg', 'Small rice cakes topped with savory shrimp and pork filling.', 50, 1),
	('caterer1@gmail.com', 'Banh Nam', 'banhnam.jpg', 'Steamed rice cakes filled with pork and mushroom mixture.', 40, 1),
	('caterer1@gmail.com', 'Banh Xeo Tom', 'banhxeotom.jpg', 'Crispy crepe filled with shrimp, pork, and bean sprouts.', 60, 1),
	('caterer1@gmail.com', 'Goi Sua', 'goisua.jpg', 'Jellyfish salad with peanuts, herbs, and a tangy dressing.', 70, 1),
	('caterer1@gmail.com', 'Nem Ran', 'nemran.jpg', 'Deep-fried spring rolls filled with pork, vegetables, and vermicelli noodles.', 40, 1),
	('caterer1@gmail.com', 'Ca Kho To', 'cakhoto.jpg', 'Braised fish in a clay pot with caramel sauce and vegetables.', 120, 1),
	('caterer1@gmail.com', 'Thi Kho Tau', 'thitkhotau.jpg', 'Caramelized pork belly with eggs and coconut water.', 100, 1),
	('caterer1@gmail.com', 'Lau Mam', 'laumam.jpg', 'Hot pot with fermented fish sauce, vegetables, and various meats or seafood.', 150, 1),
	('caterer1@gmail.com', 'Che Dau Tay', 'chedautay.jpg', 'Sweet dessert with strawberries, red beans, and coconut milk.', 40, 1),
	('caterer1@gmail.com', 'Che Dau Den', 'chedauden.jpg', 'Sweet dessert with black beans, coconut milk, and ginger.', 30, 1),
	('caterer1@gmail.com', 'Banh Trang Cuon Thit Heo', 'banhtrangcuonthitheo.jpg', 'Rice paper rolls filled with grilled pork, herbs, and dipping sauce.', 60, 1),
	('caterer1@gmail.com', 'Bun Thang', 'bunthang.jpg', 'Noodle soup with chicken, egg, and mushrooms.', 80, 1),
	('caterer1@gmail.com', 'Banh Tet', 'banhtet.jpg', 'Sticky rice cake filled with pork, mung beans, and green peas.', 50, 1),
	('caterer1@gmail.com', 'Banh Xeo Tom Thit', 'banhxeotomthit.jpg', 'Crispy crepe filled with shrimp, pork, and bean sprouts.', 70, 1),
	('caterer1@gmail.com', 'Nem Nuong Cha Gio', 'nemnuongchagio.jpg', 'Combination platter of grilled pork skewers and spring rolls.', 100, 1),
	('caterer1@gmail.com', 'Cha Ca La Vong', 'chacalavong.jpg', 'Grilled turmeric fish served with dill and vermicelli noodles.', 120, 1),
	('caterer1@gmail.com', 'Bun Oc', 'bunoc.jpg', 'Snail noodle soup with a spicy and flavorful broth.', 80, 1),
	('caterer1@gmail.com', 'Com Tam', 'comtam.jpg', 'Broken rice dish with grilled pork, fried egg, and pickled vegetables.', 60, 1),
	('caterer2@gmail.com', 'Sushi platters', 'sushi platter.jpg', 'A selection of assorted sushi pieces, perfect for sharing.', 180, 1),
	('caterer2@gmail.com', 'Sashimi platters', 'sashimi platter.jpg', 'Thinly sliced fresh fish served with condiments, a luxurious party option.', 250, 1),
	('caterer2@gmail.com', 'Chicken Karaage', 'chickenkaraage.jpg', 'Crispy fried chicken bites, a crowd favorite.', 80, 1),
	('caterer2@gmail.com', 'Ebi Tempura', 'ebitempura.jpg', 'Lightly battered and fried shrimp, a popular party appetizer.', 70, 1),
	('caterer2@gmail.com', 'Yakitori Skewers', 'yakitoriskewers.jpg', 'Grilled skewered meat or vegetables, great for finger food.', 60, 1),
	('caterer2@gmail.com', 'Gyoza', 'gyoza.jpg', 'Japanese dumplings filled with savory ingredients, easy to serve and enjoy.', 50, 1),
	('caterer2@gmail.com', 'Mochi', 'mochi.jpg', 'Sweet rice cakes with various fillings, a delightful party dessert.', 40, 1),
	('caterer3@gmail.com', 'Chicken Tikka Masala', 'chickentikkamasala.jpg', 'Creamy tomato-based curry with tender chicken pieces.', 150, 0),
	('caterer3@gmail.com', 'Butter Chicken', 'butterchicken.jpg', 'Rich and buttery chicken curry with a hint of sweetness.', 140, 0),
	('caterer3@gmail.com', 'Vegetable Samosas', 'vegetablesamosas.jpg', 'Crispy fried pastries filled with savory vegetables.', 60, 1),
	('caterer3@gmail.com', 'Naan Bread', 'naanbread.jpg', 'Soft and fluffy flatbread perfect for scooping up curries.', 30, 1),
	('caterer3@gmail.com', 'Tandoori Chicken', 'tandoorichicken.jpg', 'Chicken marinated in yogurt and spices, grilled to perfection.', 120, 1),
	('caterer3@gmail.com', 'Mango Lassi', 'mangolassi.jpg', 'Refreshing yogurt-based drink with mango chunks.', 40, 1),
	('caterer3@gmail.com', 'Gulab Jamun', 'gulabjamun.jpg', 'Sweet dumplings soaked in rose-flavored syrup.', 50, 1),
	('caterer4@gmail.com', 'Antipasto Platter', 'antipastoplatter.jpg', 'An assortment of cured meats, cheeses, olives, and marinated vegetables.', 180, 1),
	('caterer4@gmail.com', 'Bruschetta', 'bruschetta.jpg', 'Toasted bread topped with fresh tomatoes, basil, and garlic.', 60, 1),
	('caterer4@gmail.com', 'Caprese Salad', 'caprese salad.jpg', 'A classic salad with mozzarella, tomatoes, and basil.', 50, 1),
	('caterer4@gmail.com', 'Pasta Primavera', 'pastaprimavera.jpg', 'Pasta with a medley of fresh vegetables.', 120, 1),
	('caterer4@gmail.com', 'Lasagna', 'lasagna.jpg', 'Layers of pasta, meat sauce, cheese, and b�chamel sauce.', 140, 1),
	('caterer4@gmail.com', 'Tiramisu', 'tiramisu.jpg', 'Coffee-flavored dessert with layers of ladyfingers, mascarpone cream, and cocoa powder.', 70, 1),
	('caterer4@gmail.com', 'Gelato', 'gelato.jpg', 'Italian ice cream with a dense and creamy texture.', 40, 1),
	('caterer5@gmail.com', 'Guacamole and Chips', 'guacamoleandchips.jpg', 'Creamy avocado dip with tortilla chips.', 60, 1),
	('caterer5@gmail.com', 'Quesadillas', 'quesadillas.jpg', 'Tortillas filled with cheese, meat, or vegetables.', 50, 1),
	('caterer5@gmail.com', 'Tacos', 'tacos.jpg', 'Corn tortillas filled with your choice of protein, toppings, and salsa.', 70, 1),
	('caterer5@gmail.com', 'Burritos', 'burritos.jpg', 'Large flour tortillas filled with rice, beans, meat, and vegetables.', 80, 1),
	('caterer5@gmail.com', 'Enchiladas', 'enchiladas.jpg', 'Corn tortillas filled with meat or cheese, topped with enchilada sauce.', 120, 1),
	('caterer5@gmail.com', 'Churros', 'churros.jpg', 'Fried dough pastries served with cinnamon sugar and dipping sauces.', 40, 1)

select * from Dish

insert into OrderDetails values
	(1, 1, 6, 100),
	(2, 1, 6, 120),
	(3, 1, 6, 80),
	(1, 2, 2, 100),
	(2, 2, 2, 120),
	(1, 3, 5, 100),
	(31, 4, 2, 180),
	(38, 5, 2, 150),
	(45, 6, 2, 180),
	(52, 7, 2, 60),
	(1, 8, 2, 100)

select * from OrderDetails

insert into MinigameReward values
	(0, 10000000),
	(1, 1000000),
	(2, 100000),
	(5, 10000),
	(10, 1000),
	(20, 100),
	(50, 10),
	(100, 1)

select * from MinigameReward

insert into Report values
	('caterer1@gmail.com', 'customer2@gmail.com', 'This customer is a scammer', '2024-05-15', 1),
	('customer3@gmail.com', 'caterer2@gmail.com', 'This caterer don''t offer service as stated', '2024-04-20', 1),
	('caterer4@gmail.com', 'customer1@gmail.com', 'This customer is a spammer', '2024-05-18', 0),
	('customer3@gmail.com', 'caterer5@gmail.com', 'This caterer''s service is unacceptable', '2024-05-18', 0),
	('caterer1@gmail.com', 'customer4@gmail.com', 'Just a report', '2024-05-17', 1),
	('caterer3@gmail.com', 'customer2@gmail.com', 'I report him', '2024-05-16', 0),
	('customer1@gmail.com', 'caterer2@gmail.com', 'Discrimination', '2024-04-25', 0)

select * from Report

insert into Notification values
	('customer1@gmail.com', 'caterer1@gmail.com', 'Customer Duc Anh Tran has ordered a catering service', '2024-06-01 07:00:00'),
	('customer3@gmail.com', 'caterer1@gmail.com', 'Customer Binh Chi Lam has ordered a catering service', '2024-05-15 07:30:00'),
	('caterer1@gmail.com', 'customer3@gmail.com', 'We''ve received your order, please conduct payment for the order 2 via NCB 0123456798 with transaction details Plate Portal order 2', '2024-05-15 08:00:00'),
	('customer3@gmail.com', 'caterer1@gmail.com', 'I''ve conducted payment for order 2', '2024-05-15 08:00:00'),
	('customer1@gmail.com', 'caterer1@gmail.com', 'Customer Duc Anh Tran has ordered a catering service', '2024-05-15 09:52:13'),
	('caterer1@gmail.com', 'customer1@gmail.com', 'We''ve received your order, please conduct payment for the order 2 via NCB 0123456798 with transaction details Plate Portal order 1', '2024-06-01 10:06:15')

select * from Notification

insert into PaymentType values
	('Rank'),
	('Commission'),
	('Advertise')

select * from PaymentType

insert into PaymentHistory values
	('caterer1@gmail.com', 1, 15, '2022-02-10 09:56:23', 'Rank buy 1'),
	('caterer2@gmail.com', 1, 15, '2022-02-01 10:56:12', 'Rank buy 1'),
	('caterer3@gmail.com', 1, 15, '2022-02-01 17:56:23', 'Rank buy 1'),
	('caterer4@gmail.com', 1, 15, '2022-02-01 08:32:00', 'Rank buy 1'),
	('caterer5@gmail.com', 1, 15, '2022-02-01 05:56:42', 'Rank buy 1'),
	('caterer1@gmail.com', 1, 15, '2024-04-17 09:56:23', 'Rank buy 1'),
	('caterer1@gmail.com', 1, 15, '2024-05-17 09:56:23', 'Rank buy 1'),
	('caterer1@gmail.com', 1, 15, '2024-06-17 12:13:45', 'Rank buy 1'),
	('caterer2@gmail.com', 1, 30, '2024-03-10 10:56:12', 'Rank buy 2'),
	('caterer2@gmail.com', 1, 30, '2024-05-01 10:56:12', 'Rank buy 2'),
	('caterer3@gmail.com', 1, 100, '2024-02-20 17:26:23', 'Rank buy 3'),
	('caterer3@gmail.com', 1, 100, '2024-05-01 17:56:23', 'Rank buy 3'),
	('caterer3@gmail.com', 1, 100, '2024-06-01 11:23:23', 'Rank buy 3'),
	('caterer4@gmail.com', 1, 15, '2024-02-18 09:15:00', 'Rank buy 1'),
	('caterer4@gmail.com', 1, 15, '2024-04-01 08:32:00', 'Rank buy 1'),
	('caterer5@gmail.com', 1, 15, '2024-02-15 15:56:42', 'Rank buy 1'),
	('caterer5@gmail.com', 1, 15, '2024-05-01 05:56:42', 'Rank buy 1'),
	('caterer5@gmail.com', 1, 15, '2024-06-01 05:56:42', 'Rank buy 1'),
	('caterer1@gmail.com', 1, 15, '2024-02-17 09:59:36', 'Rank buy 1'),
	('caterer1@gmail.com', 2, 2, '2024-05-15 07:56:23', 'Order accept 2'),
	('caterer1@gmail.com', 2, 2, '2024-05-15 10:06:05', 'Order accept 3'),
	('caterer2@gmail.com', 2, 1, '2024-03-10 10:06:05', 'Order accept 4'),
	('caterer4@gmail.com', 2, 2, '2024-02-25 12:56:08', 'Order accept 6'),
	('caterer5@gmail.com', 2, 2, '2024-02-25 08:06:05', 'Order accept 7'),
	('caterer1@gmail.com', 2, 2, '2024-02-25 09:06:05', 'Order accept 8'),
	('caterer1@gmail.com', 3, 100, '2024-05-01 19:06:05', 'Banner buy 1'),
	('caterer2@gmail.com', 3, 50, '2024-05-15 16:26:05', 'Banner buy 2'),
	('caterer3@gmail.com', 3, 50, '2024-05-20 19:06:05', 'Banner buy 3'),
	('caterer4@gmail.com', 3, 100, '2024-05-20 19:06:05', 'Banner buy 4'),
	('caterer5@gmail.com', 3, 50, '2024-05-01 12:56:03', 'Banner buy 5'),
	('caterer1@gmail.com', 3, 40, '2024-05-01 19:06:05', 'Banner buy 6'),
	('caterer2@gmail.com', 3, 100, '2024-05-10 19:56:05', 'Banner buy 7'),
	('caterer3@gmail.com', 3, 50, '2024-05-15 05:06:56', 'Banner buy 8'),
	('caterer5@gmail.com', 3, 50, '2024-05-20 17:06:05', 'Banner buy 9'),
	('caterer5@gmail.com', 3, 100, '2024-05-25 19:06:05', 'Banner buy 10')

select * from PaymentHistory