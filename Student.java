public class Student implements Student_{

   String firstName;
   String lastName;
   String hostel;
   String department;
   String cgpa;

   public Student(String firstName, String lastName, String hostel, String department, String cgpa) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.hostel = hostel;
      this.cgpa = cgpa;
      this.department = department;
   }

   // Return student’s first name 
   public String fname() {
      return this.firstName;
   }

// Return student’s last name
   public String lname() {
      return this.lastName;
   }

   // Return student’s hostel name 
   public String hostel() {
      return this.hostel;
   } 

   // Return student’s department name 
   public String department() {
      return this.department;
   }

   public String cgpa() {
      return this.cgpa;
   }
}