## Office Management App

- Uygulama, bir ofisteki düzen, temizlik gibi işleri organize etmek için tasarlandı. <br/>
- Kullanıcı, görevli ve admin olmak üzere üç farklı kullanıcı girişi mevcut.<br/>
- Admin, ofise ait departman ekleyebilir, departmanlarda ne tür işler olacağını seçebilir, kullanıcı oluşturabilir, departmanlardaki işlerin durumlarını (yapılıp yapılmadığını) kontrol edebilir. <br/>
- Admin, departmanlara işleri ekleyebilir. Bir departmana ait kullanıcının başka hangi departmanlara ait işleri görebileceğini seçebilir. <br/>
- Kullanıcı kendi ve erişebildiği diğer departmanlara ait işleri görüntüleyebilir, işlerin yapılıp yapılmadığını kontrol edebilir, işi görevliye raporlayabilir. <br/>
- Kullanıcı eksik işleri görevliye bildirebilir. (görevliye bildirim gönderilir) <br/>
- Raporlanan iş olduğunda görevliye - xxx departmanındaki xxx işi raporlandı - bildirimi gönderilir, görevli işi tamamladığında raporlanan işe ait departmandaki kişilere tekrar -
xxx raporlanan işi yapıldı - diye bildirim gönderilir. <br/>
- Görevli raporlanmış işi onayladığında, raporlanan departmana ait kullanıcılara, işin yapıldığına dair bildirim gönderilir. <br/>

- Veritabanı olarak SQL Server kullandım. <br/>
- Veritabanı ile uygulamayı kendi yazdığım web api ile bağladım. 

**-----------------------------------**

- The app is designed to organize things like organization, cleaning and so on in an office.
- There are three different user entries, user, person, and admin.
- Admin can add departments to the office, select what kind of jobs in departments, create users, check the status of jobs in departments (whether they have been done).
- Admin can add jobs to departments. The user of a department can select which other departments can see jobs.
- The user can view jobs from their own and other departments that they can access, check if jobs are done, and report the job to the employee.
- The user can report missing jobs to the employee. (notification sent to the attendant)
- When there is a reported job, the employee (xxx jobs reported in xxx department) is notified, and when the agent completes the job, the reports to the people in the department of the reported job are sent again (xxx reported job done).
- When the agent approves the reported job, users of the reported department will be notified that the job has been done.

- I used SQL Server as a database.
- I've connected the database and the application with the web api I wrote.


√ View Binding <br/>
√ Dagger Hilt <br/>
√ Retrofit <br/>
√ MVVM <br/>
√ Coroutines <br/>
√ Notification (OneSignal) <br/>
√ Lottie <br/>

**--- Login Screen ---** <br/>
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/login%20screen.JPG) <br/>
**--- Departments Screen ---** <br/>
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/departments%20screen.JPG)
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/departments%20screen%202.JPG)
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/insert%20department.JPG) <br/>
**--- Add Access To Department ---** <br/>
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/insert%20access.JPG) <br/>
**--- Add Job To Department ---** <br/>
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/insert%20work.JPG) <br/>
**--- Jobs Screen ---** <br/>
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/work%20screen.JPG) <br/>
**--- Add User ---** <br/>
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/insert%20user.JPG) <br/>
**--- Jobs Status ---** <br/>
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/admin%20work%20status.JPG) <br/>
**--- User Screen ---** <br/>
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/user%20screen.JPG)
![Resim](https://github.com/Sedat-Uluisik/OfficeManagementApp/blob/main/art/user%20report%20screen.JPG)
