package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import accident.Accident;
import accident.AccidentList;
import contract.Contract;
import contract.ContractList;
import customer.Building;
import customer.Car;
import customer.Customer;
import customer.CustomerList;
import customer.Driver;
import dao.AccidentDao;
import dao.AccidentDaoImpl;
import dao.ApproveDao;
import dao.ApproveDaoImpl;
import dao.BuildingDao;
import dao.BuildingDaoImpl;
import dao.CarDao;
import dao.CarDaoImpl;
import dao.ContractDao;
import dao.ContractDaoImpl;
import dao.CoverageDao;
import dao.CoverageDaoImpl;
import dao.CustomerDao;
import dao.CustomerDaoImpl;
import dao.DamageDao;
import dao.DamageDaoImpl;
import dao.DriverDao;
import dao.DriverDaoImpl;
import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import dao.ExemptionDao;
import dao.ExemptionDaoImpl;
import dao.InsuranceDao;
import dao.InsuranceDaoImpl;
import dao.PCustomerDao;
import dao.PCustomerDaoImpl;
import dao.SaleRecordDao;
import dao.SaleRecordDaoImpl;
import employee.Employee;
import employee.EmployeeList;
import exemption.Exemption;
import insurance.Approve;
import insurance.Coverage;
import insurance.Insurance;
import insurance.InsuranceList;
import insurance.SaleRecord;
import pCustomer.PCustomer;
import pCustomer.PCustomerList;
import reward.RewardInfo;

public class IMain {
   private static Employee employee;
   private static InsuranceList insuranceList;
   private static CustomerList customerList;
   private static PCustomerList pCustomerList;
   private static AccidentList accidentList;
   private static EmployeeList employeeList;
   private static ContractList contractList;

   // dao
   private static AccidentDao accidentDao;
   private static ContractDao contractDao;
   private static CustomerDao customerDao;
   private static BuildingDao buildingDao;
   private static CarDao carDao;
   private static DriverDao driverDao;
   private static InsuranceDao insuranceDao;
   private static SaleRecordDao saleRecordDao;
   private static CoverageDao coverageDao;
   private static ApproveDao approveDao;
   private static PCustomerDao pCustomerDao;
   private static ExemptionDao exemptionDao;
   private static DamageDao damageDao;
   private static EmployeeDao employeeDao;

   private static int uwRate;
   private static Scanner sc;
   private static boolean dataflag; // dataflag�� ��/��å �Ǵܿ��� ��񿡼� �߸��� ���� �ִ��� Ȯ�ο�.

   public IMain() {
      sc = new Scanner(System.in);
      uwRate = 0;
      dataflag = false;

      accidentDao = new AccidentDaoImpl();
      contractDao = new ContractDaoImpl();
      customerDao = new CustomerDaoImpl();
      buildingDao = new BuildingDaoImpl();
      carDao = new CarDaoImpl();
      driverDao = new DriverDaoImpl();
      insuranceDao = new InsuranceDaoImpl();
      saleRecordDao = new SaleRecordDaoImpl();
      coverageDao = new CoverageDaoImpl();
      approveDao = new ApproveDaoImpl();
      pCustomerDao = new PCustomerDaoImpl();
      exemptionDao = new ExemptionDaoImpl();
      damageDao = new DamageDaoImpl();
      employeeDao = new EmployeeDaoImpl();
   }

   public static void setDBData() {
      pCustomerList = pCustomerDao.retrieve();
      customerList = customerDao.retrieve();
      insuranceList = insuranceDao.retrieve();
      accidentList = accidentDao.retrieve();
      contractList = contractDao.retrieve();
      employeeList = employeeDao.retrieve();
   }
   public static void main(String[] args) {
      IMain main = new IMain();
      boolean flag = false;
      setDBData();
      System.out.println("���� ���̵� �Է��ϼ���. ");
      employee = employeeList.search(Integer.parseInt(sc.nextLine()));
      while ( employee == null ) {
         System.out.println("�������� �ʴ� ���� ID�� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
         employee = employeeList.search(Integer.parseInt(sc.nextLine()));
      }
      int menu = checkEmployee(employee);
      for(int i =0; i<6; i++) {
         switch(menu) {
         case 1:
            // ���� ����
            System.out.println("*******���輳�� �޴� ********");
            System.out.println("1. �����ϱ�"); 
            System.out.println("2. ��ǰ �ΰ��ϱ�");
            System.out.println("3. ���� �����ϱ�");
            System.out.println("4. �����ϱ�");

            int submenu =Integer.parseInt(sc.nextLine());
            switch(submenu) {
            case 1: 
               insuranceDesign();
               break;
            case 2:
               System.out.println("***********�����ǰ�� �ΰ��ϴ� �������Դϴ�**************");
               insuranceApprove();
               break;
            case 3:
               System.out.println("***********�����ǰ�� ���İ����ϴ� �������Դϴ�**************");
               insuranceManage();
            break;
            case 4: System.out.println("���α׷��� �����մϴ�.");    
            flag = true; 
            break;
            }
            break;

         case 2:
            // UW
            System.out.println("******* UW �޴� ********");
            System.out.println("1. �μ��ɻ��ϱ�");
            System.out.println("2. �����ϱ�");
            switch(Integer.parseInt(sc.nextLine())) {
            case 2: System.out.println("���α׷��� �����մϴ�.");    
            flag = true; 
            break;
            }
            break;
         case 3:
            // ����
            System.out.println("*******���� �޴� ********");
            System.out.println("1. ����ϱ�");
            System.out.println("2. ���ü��");
            System.out.println("3. ������");
            System.out.println("4. ������");
            System.out.println("5. �����ϱ�");

            switch(Integer.parseInt(sc.nextLine())) {
            case 1: consult(); break;
            case 2: contract(0); break;
            case 3: contractManage(); break;
            case 4: customerManage(); break;
            case 5: System.out.println("���α׷��� �����մϴ�.");    
            flag = true;
            break;
            }
            break;
         case 4:
            // ����
            System.out.println("*******���� �޴� ********");
            System.out.println("1. ������Ȯ��");
            System.out.println("2. �������");
            System.out.println("3. ������������ϱ�");
            System.out.println("4. ��/��å �Ǵ��ϱ�");
            System.out.println("5. ���� �����ϱ�");
            System.out.println("6. �����ϱ�");

            switch(Integer.parseInt(sc.nextLine())) {
            case 1: checkcustomerInfo(); break;
            case 2: acceptAccident(null); break;
            case 3: investigate(); break;
            case 4: decideExemption(); break;
            case 5: damageAssessment(); break;
            case 6: System.out.println("���α׷��� �����մϴ�.");    
            flag = true; 
            break;
            }
            break;
         default:
            System.out.println("�����Ͱ� �ùٸ��� �ʽ��ϴ�. �ٽ� �Է����ּ���.");
            break;
         }
         if(flag == true) break;
      }
   }
   private static void insuranceDesign() {
      Insurance insurance = new Insurance();

      System.out.println("���� ���踦 �����մϴ�.");
      
      System.out.println("�����ϰ��� �ϴ� �����ǰ ������ �������ּ���.");
      System.out.println("1. �ڵ��� ȭ�纸��");
      System.out.println("2. �ǹ� ȭ�纸��");
      System.out.println("3. ������ ����");

      switch ( Integer.parseInt(sc.nextLine()) ) {
      case 1: insurance.setInsuranceType("car"); break;
      case 2: insurance.setInsuranceType("building"); break;
      case 3: insurance.setInsuranceType("driver"); break;
      }
      
      System.out.println("������� �Է����ּ���.");
      insurance.setInsuranceName(sc.nextLine());
      
      System.out.println("���� ������ �Է����ּ���.");
      insurance.setContents(sc.nextLine());
      
      System.out.println("���� �⺻ ���Ժ� �Է����ּ���.");
      insurance.setInsuranceCost(sc.nextLine());
      
      int insuranceID = insuranceDao.create(insurance);
      if ( insuranceID == 0 ) return; // 0�̶�� ���� ���� �߸��Ǿ��ٴ� ��!
      
      insurance.setInsuranceID(insuranceID);
      
      Coverage hcoverage = new Coverage();
      hcoverage.setCoverageCondition("high");
      System.out.println("������� ������ ���� ����� ���峻���� �Է����ּ���.");
      hcoverage.setCoverageContent(sc.nextLine());
      System.out.println("������� ������ ���� ����� �ִ뺸��ݾ��� �Է����ּ���.");
      hcoverage.setCoverageCost(Integer.parseInt(sc.nextLine()));
      hcoverage.setInsuranceID(insuranceID);
      int hcoverageID = coverageDao.create(hcoverage);
      hcoverage.setCoverageID(hcoverageID);
      insurance.setM_hcoverage(hcoverage);

      Coverage mcoverage = new Coverage();
      mcoverage.setCoverageCondition("middle");
      System.out.println("������� ������ �߰��� ����� ���峻���� �Է����ּ���.");
      mcoverage.setCoverageContent(sc.nextLine());
      System.out.println("������� ������ �߰��� ����� �ִ뺸��ݾ��� �Է����ּ���.");
      mcoverage.setCoverageCost(Integer.parseInt(sc.nextLine()));
      mcoverage.setInsuranceID(insuranceID);
      int mcoverageID = coverageDao.create(mcoverage);
      mcoverage.setCoverageID(mcoverageID);
      insurance.setM_mcoverage(mcoverage);

      Coverage lcoverage = new Coverage();
      lcoverage.setCoverageCondition("low");
      System.out.println("������� ������ ���� ����� ���峻���� �Է����ּ���.");
      lcoverage.setCoverageContent(sc.nextLine());
      System.out.println("������� ������ ���� ����� �ִ뺸��ݾ��� �Է����ּ���.");
      lcoverage.setCoverageCost(Integer.parseInt(sc.nextLine()));
      lcoverage.setInsuranceID(insuranceID);
      int lcoverageID = coverageDao.create(lcoverage);
      lcoverage.setCoverageID(lcoverageID);
      insurance.setM_lcoverage(lcoverage);
      
      insuranceList.add(insurance);
      System.out.println("���� ���谡 �Ϸ�Ǿ����ϴ�.");
   }
   
   private static void insuranceApprove() {
      // �ΰ��� ������� ���� ���� ��� ��������
            ArrayList<Insurance> insurances = insuranceDao.retrieveNoApprove().getInsuranceList();
            
            System.out.println("***** ���� ��� *****");
            for ( Insurance insurance : insurances ) { System.out.println( insurance.getInsuranceID()+" "+insurance.getInsuranceName());}

            System.out.println("��ǰ �ΰ��� ������ ���� ID�� �Է��ϼ���.");
            Insurance insurance = insuranceList.search(Integer.parseInt(sc.nextLine()));
            while ( insurance == null ) {
               System.out.println("�������� �ʴ� ����ID�� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
               insurance = insuranceList.search(Integer.parseInt(sc.nextLine()));
            }
            
            System.out.println("***** �������� *****");
            System.out.println("���� ID : " + insurance.getInsuranceID());
            System.out.println("����� : " + insurance.getInsuranceName());
            System.out.println("�������� : " + insurance.getInsuranceType());
            System.out.println("���� �⺻ ���Ժ� : " + insurance.getInsuranceCost());
            System.out.println("���賻�� : " + insurance.getContents());
            System.out.println("�� - ���峻�� : " + insurance.getM_hcoverage().getCoverageContent());
            System.out.println("�� - �ִ뺸��ݾ� : " + insurance.getM_hcoverage().getCoverageCost());
            System.out.println("�� - ���峻�� : " + insurance.getM_mcoverage().getCoverageContent());
            System.out.println("�� - �ִ뺸��ݾ� : " + insurance.getM_mcoverage().getCoverageCost());
            System.out.println("�� - ���峻�� : " + insurance.getM_lcoverage().getCoverageContent());
            System.out.println("�� - �ִ뺸��ݾ� : " + insurance.getM_lcoverage().getCoverageCost());
            
            System.out.println("��ǰ�ΰ��� �����Ͻ÷��� 1��, �����Ͻ÷��� 2��, ����Ͻ÷��� 3�� �Է����ּ���");
            
            SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
            Date time = new Date();
            Approve approve = new Approve();
            
            switch(Integer.parseInt(sc.nextLine())) {
            case 1 :
               approve.setInsuranceID(insurance.getInsuranceID());
               approve.setApproved(1);
               approve.setPermissionDate(format.format(time));
               System.out.println(format.format(time) + "�� " + insurance.getInsuranceName() + "������ �ΰ� ���εǾ����ϴ�." );
               insurance.setM_approve(approve);
               insuranceList.delete(insurance.getInsuranceID());
               approveDao.create(approve);
               
               break;
            case 2 : 
               System.out.println("��ǰ �ΰ��� �����ϼ̽��ϴ�.");
               approve.setInsuranceID(insurance.getInsuranceID());
               approve.setApproved(0);
               approve.setPermissionDate(format.format(time));
               System.out.println("���� ������ �Է����ּ���.");
               approve.setPermissionRefuse(sc.nextLine());
               System.out.println("������ �������� �Է����ּ���.");
               approve.setInsuranceProblem(sc.nextLine());
               System.out.println(format.format(time) + "�� " + insurance.getInsuranceName() + "���� �ΰ��� �ź��ϼ̽��ϴ�." );
               insurance.setM_approve(approve);
               insuranceList.delete(insurance.getInsuranceID());
               approveDao.create(approve);
               break;
            case 3 : 
               System.out.println("������� �����Դϴ�.");
               break;
            }
   }
   
   private static void insuranceManage() {
      System.out.println("********** ���� ��� ************");
      ArrayList<Insurance> insurances = insuranceDao.retrieveApprove().getInsuranceList();
      for ( Insurance insurance : insurances ) { System.out.println( insurance.getInsuranceID()+" "+insurance.getInsuranceName());}

      System.out.println("�ǸŽ���ǥ�� �ۼ��� ���� ID�� �Է��ϼ���.");
      Insurance insurance = insuranceList.search(Integer.parseInt(sc.nextLine()));
      while ( insurance == null ) {
         System.out.println("�������� �ʴ� ���� ID�� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
         insurance = insuranceList.search(Integer.parseInt(sc.nextLine()));
      }
      
      System.out.println("***** �������� *****");
      System.out.println("���� ID : " + insurance.getInsuranceID());
      System.out.println("����� : " + insurance.getInsuranceName());
      System.out.println("�������� : " + insurance.getInsuranceType());
      System.out.println("���� �⺻ ���Ժ� : " + insurance.getInsuranceCost());
      System.out.println("���賻�� : " + insurance.getContents());
      System.out.println("�� - ���峻�� : " + insurance.getM_hcoverage().getCoverageContent());
      System.out.println("�� - �ִ뺸��ݾ� : " + insurance.getM_hcoverage().getCoverageCost());
      System.out.println("�� - ���峻�� : " + insurance.getM_mcoverage().getCoverageContent());
      System.out.println("�� - �ִ뺸��ݾ� : " + insurance.getM_mcoverage().getCoverageCost());
      System.out.println("�� - ���峻�� : " + insurance.getM_lcoverage().getCoverageContent());
      System.out.println("�� - �ִ뺸��ݾ� : " + insurance.getM_lcoverage().getCoverageCost());
      
      System.out.println("�ǸŽ���ǥ�� �ۼ��Ͻ÷��� 1��, ����Ͻ÷��� 2��, ����Ͻ÷��� 3�� �Է����ּ���.");
      
      switch ( Integer.parseInt(sc.nextLine()) ) {
      case 1:
         if ( insurance.getM_SaleRecord().getInsuranceID() != 0 ) {
            System.out.println("�ǸŽ���ǥ�� �̹� ��ϵǾ� �ֽ��ϴ�.");
            
            System.out.println("*********�ǸŽ���ǥ*********");
            System.out.println("��ǥ ���� : " + insurance.getM_SaleRecord().getGoalCnt());
            System.out.println("�޼� ���� : " + insurance.getM_SaleRecord().getSaleCnt());
            System.out.println("�޼��� : " + ( (double)insurance.getM_SaleRecord().getSaleCnt() / (double)insurance.getM_SaleRecord().getGoalCnt() ) * 100);
         }
         else {
            SaleRecord saleRecord = new SaleRecord();
            System.out.println("�ش� ������ ��ǥ ������ �Է��Ͻʽÿ�.");
            saleRecord.setGoalCnt(Integer.parseInt(sc.nextLine()));
            System.out.println("�ش� ������ �Ǹ� ������ �Է��Ͻʽÿ�.");
            saleRecord.setSaleCnt(Integer.parseInt(sc.nextLine()));
            
            System.out.println("�Է��Ͻ� �ǸŽ���ǥ�� ������ �Ʒ��� �����ϴ�. �����ø� 1��, Ʋ���� 2�� �Է����ֽʽÿ�.");
            System.out.println("*********�ǸŽ���ǥ*********");
            System.out.println("��ǥ ���� : " + saleRecord.getGoalCnt());
            System.out.println("�޼� ���� : " + saleRecord.getSaleCnt());
            System.out.println("�޼��� : " + ( (double)saleRecord.getSaleCnt() / (double)saleRecord.getGoalCnt() ) * 100);
         
            if ( Integer.parseInt(sc.nextLine() ) == 1 ) {
               saleRecord.setInsuranceID(insurance.getInsuranceID());
               saleRecordDao.create(saleRecord);
               insurance.setM_SaleRecord(saleRecord);
               System.out.println("�ǸŽ���ǥ �ۼ��� �Ϸ�Ǿ����ϴ�.");
            }
         }
         break;
         
      case 2: System.out.println("�ǸŽ���ǥ �ۼ��� ��ҵǾ����ϴ�."); break;
      case 3: break;
      }
   }
   private static int checkEmployee(Employee employee) {
      String number = String.valueOf(employee.getEmployeeID());
      char[] nums = number.toCharArray();
      if(nums[0] == '1') {
         System.out.println("�����ȣ : " + employee.getEmployeeID() + " �����: " + employee.getName());
         return 1;
      } else if(nums[0] == '2') {
         System.out.println("�����ȣ : " + employee.getEmployeeID() + " �����: " + employee.getName());
         return 2;
      } else if(nums[0] == '3') {
         System.out.println("�����ȣ : " + employee.getEmployeeID() + " �����: " + employee.getName());
         return 3;
      } else if(nums[0] == '4') {
         System.out.println("�����ȣ : " + employee.getEmployeeID() + " �����: " + employee.getName());
         return 4;
      }
      return 0;
   }
   // U/W
   private static int calcualteFactors(Insurance insurance, Customer customer) {
      int count = 1;
      int finalRate=0;
      int cAccidentCount = customer.getAccident().size();
      switch(insurance.getInsuranceType()) {
      case "building":
         // ȯ���� ���� ��� > �ֱ� 3�� �̳��� ��� �߻� Ƚ��
         if(cAccidentCount > 0) {
            if(cAccidentCount == 1) uwRate = 4;
            else if(cAccidentCount == 2) uwRate = 3;
            else if(cAccidentCount == 3) uwRate = 2;
            else uwRate = 1;
         } else uwRate = 0;
         System.out.println("ȯ�� ����: " + uwRate);
         finalRate = uwRate;
         break;
      case "car":
         // ȯ���� ���� ��� >  ���/��� �̷�
         if(cAccidentCount > 0) {
            if(cAccidentCount == 1) uwRate += 4;
            else if(cAccidentCount == 2) uwRate += 3;
            else if(cAccidentCount == 3) uwRate += 2;
            else uwRate += 1;
         } else uwRate = 0;
         finalRate += uwRate;
         System.out.println("ȯ�� ����: " + uwRate);
         // ������
         if(customer.getM_car().isOwn()==1) {
            switch(customer.getJob()) {
            case "�л�": 
               if(uwRate != 0) uwRate += 1;
               break;
            case "�繫��": // �繫��
               if(uwRate != 0) uwRate += 1;
               break;
            case "���": // ���
               if(uwRate > 2) uwRate -= 1;
               else uwRate = 2;
               break;
            case "ȭ����������": // ȭ���� ������
               if(uwRate > 3) uwRate -= 1;
               else uwRate = 3;
               break;
            case "���������": // ��� ������
               if(uwRate > 3) uwRate -= 1;
               else uwRate = 3;
               break;
            default:
               break;
            }
            count++;
            finalRate += uwRate;
         }
         System.out.println("���� ����: " + uwRate);
         break;
      case "driver":
         // ȯ���� ���� ��� >  ���/��� �̷�
         if(cAccidentCount > 0) {
            if(cAccidentCount == 1) uwRate += 4;
            else if(cAccidentCount == 2) uwRate += 3;
            else if(cAccidentCount == 3) uwRate += 2;
            else uwRate += 1;
         } else uwRate = 0;

         finalRate += uwRate;
         System.out.println("ȯ�� ����: " + uwRate);
         // ���� ����
         int joinAge = calcualteCustomerAge(customer);
         if(joinAge >= 20 && joinAge < 30) {
            if(uwRate == 0 ) {uwRate = 2;}
            else if(1 < uwRate && uwRate <= 4) {uwRate -= 1;}
            else { uwRate = 1;}
         } else if(joinAge >= 30 && joinAge < 40) {
            if(uwRate == 0 ) {uwRate = 4;}
            else if(1 < uwRate && uwRate <= 4) {uwRate -= 1;}
            else { uwRate = 1;}
         }
         else if(joinAge >= 40 && joinAge < 50) {
            if(uwRate == 0 ) {uwRate = 4;}
            else if(1 < uwRate && uwRate <= 4) {uwRate -= 1;}
            else { uwRate = 1;}
         }
         else if(joinAge >= 50 && joinAge < 60) {
            if(uwRate == 0 ) {uwRate = 4;}
            else if(1 < uwRate && uwRate <= 4) {uwRate -= 1;}
            else { uwRate = 1;}
         }
         else if(joinAge >= 60) {
            if(uwRate == 0 ) {uwRate = 3;}
            else if(1 < uwRate && uwRate <= 4) {uwRate -= 1;}
            else { uwRate = 1;}
         }
         count++;
         finalRate += uwRate;
         System.out.println("���� ����: " + uwRate);
         // ���� ����
         if(customer.getM_car().isOwn() == 1 && uwRate > 1) {
            switch(customer.getM_car().getCarType()) {
            case "small": // �¿���
               if(customer.getM_driver().getCarPurpose() == "personal") uwRate += 0;
               else if(customer.getM_driver().getCarPurpose() == "official") uwRate -= 1;
               break;
            case "van": // ������
               if(customer.getM_driver().getCarPurpose() == "personal") uwRate += 0;
               else if(customer.getM_driver().getCarPurpose() == "official") uwRate -= 1;
               break;
            case "freightCar": // ȭ����
               uwRate -= 1;
               break;
            case "motocycle": // �������
               if(customer.getM_driver().getCarPurpose() == "personal") uwRate += 0;
               else if(customer.getM_driver().getCarPurpose() == "official") uwRate -= 1;
               break;
            case "agricultureCar": // �����
               uwRate -= 1;
               break;
            default:
               break;
            }
         }
         count++;
         finalRate += uwRate;
         System.out.println("���� ����: " + uwRate);
         break;
      default: break;
      }
      return (int)finalRate/count;
   }
   // ����
   private static void consult() {
      System.out.println("****** ��� ��� ���� �� ����Ʈ ******");
      ArrayList<PCustomer> pCustomers = pCustomerList.getCustomerList();
      for ( PCustomer pCustomer : pCustomers ) { System.out.println( pCustomer.getPCustomerID() + " " + pCustomer.getCustomerName() + " " + pCustomer.getPhoneNumber() + " " + pCustomer.getDate() ); }

      System.out.println("����ϰ��� �ϴ� ���� ID�� �Է��ϼ���.");

      PCustomer pCustomer = pCustomerList.search(Integer.parseInt(sc.nextLine()));
      while ( pCustomer == null ) {
         System.out.println("�������� �ʴ� ����� ID�� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
         pCustomer = pCustomerList.search(Integer.parseInt(sc.nextLine()));
      }
      pCustomerList.delete(pCustomer.getPCustomerID());

      Customer customer = new Customer();
      customer.setPCustomerID(pCustomer.getPCustomerID());
      customer.setCustomerName(pCustomer.getCustomerName());
      customer.setPhoneNumber(pCustomer.getPhoneNumber());
      customer.setDate(pCustomer.getDate());

      System.out.println("***** ������ ���� ������ *****");
      System.out.println("����� ID : " + customer.getPCustomerID());
      System.out.println("�̸� : " + customer.getCustomerName());
      System.out.println("��ȭ��ȣ : " + customer.getPhoneNumber());
      System.out.println("����û��¥ : " + customer.getDate());

      System.out.println("***** ��� ���� �Է� ******");
      System.out.println("�ּҸ� �Է��ϼ���.");
      customer.setAddress(sc.nextLine());
      System.out.println("�ֹι�ȣ�� �Է��ϼ���.(- ����)");
      customer.setCustomerNumber(sc.nextLine());
      System.out.println("������ �Է��ϼ���. ��/��");
      customer.setSex(sc.nextLine());
      System.out.println("������ �Է��ϼ���.");
      customer.setJob(sc.nextLine());
      System.out.println("�̸��� �ּҸ� �Է��ϼ���.");
      customer.seteMail(sc.nextLine());
      System.out.println("��� ������ �Է��ϼ���.");
      String context = sc.nextLine();
      customer.setConsultContext(context);

      pCustomer.setConsultContext(context);
      pCustomerDao.updateID(pCustomer.getPCustomerID(), pCustomer);

      int customerID = customerDao.create(customer);
      customer.setCustomerID( customerID );
      customerList.add(customer);

      System.out.println("��� �Ϸ�");
   }
   private static void contract( int ID ) {
      System.out.println("****** ��� ü�� �� �� ����Ʈ ******");
      ArrayList<Customer> customers = customerList.getCustomerList();
      for ( Customer customer : customers ) { System.out.println( customer.getCustomerID()+" "+customer.getDate()+" "+customer.getCustomerName() );}

      Customer customer;
      if ( ID == 0 ) {
         System.out.println("����� ü���� ���� ID�� �Է��ϼ���.");

         customer = customerList.search(Integer.parseInt(sc.nextLine()));
         while ( customer == null ) {
            System.out.println("�������� �ʴ� ��ID�� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
            customer = customerList.search(Integer.parseInt(sc.nextLine()));
         }
      }
      else customer = customerList.search(ID);

      System.out.println("***** ���� �Ż� ���� *****");
      System.out.println("�� ID : " + customer.getCustomerID());
      System.out.println("�̸� : " + customer.getCustomerName());
      System.out.println("�ּ� : " + customer.getAddress());
      System.out.println("�ֹι�ȣ : " + customer.getCustomerNumber());
      System.out.println("���� : " + customer.getSex());
      System.out.println("���� : " + customer.getJob());
      System.out.println("***** ��� ���� *****");
      System.out.println("��㳯¥ : " + customer.getDate());
      System.out.println("��㳻�� : " + customer.getConsultContext() + "\n");
      // ���� ���� �̸� �ʿ�.

      System.out.println("***** ���� ��� *****");
      ArrayList<Insurance> insurances = insuranceDao.retrieveApprove().getInsuranceList();
      for ( Insurance insurance : insurances ) { System.out.println( insurance.getInsuranceID()+" "+insurance.getInsuranceName());}

      System.out.println("����� ü���� ������ ID�� �Է��ϼ���.");
      Insurance insurance = insuranceList.search(Integer.parseInt(sc.nextLine()));
      while ( insurance == null ) {
         System.out.println("�������� �ʴ� ����ID�� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
         insurance = insuranceList.search(Integer.parseInt(sc.nextLine()));
      }

      // �� ���� �Է�
      Customer ccustomer = contractCustomer(insurance, customer);
      System.out.println("�μ��ɻ縦 �����մϴ�.");
      int uwRate = calcualteFactors(insurance, ccustomer); // �μ��ɻ��ϴ� �Լ�

      if ( uwRate == 1 ) {
         System.out.println("�μ��ɻ� ��� ����� ü���� �� �����ϴ�. �ʱ�ȭ������ ���ư��ϴ�.");
         return;
      }
      else System.out.println("�μ��ɻ� ��� ����� ü���� �� �ִ� ���Դϴ�. ��������� �����մϴ�.");

      SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
      Date time = new Date();

      Contract contract = new Contract();
      contract.setCustomer(ccustomer);
      contract.setInsurance(insurance);
      contract.setDate(format.format(time));

      float insuranceRatio = contract.calculateRatio();
      int finalPrice = 0;
      if ( insuranceRatio != 0 ) finalPrice = (int) ( Integer.parseInt(insurance.getInsuranceCost()) * insuranceRatio );
      else finalPrice = Integer.parseInt(insurance.getInsuranceCost());

      System.out.println("å���� �� ���Ժ����� " + finalPrice + "�Դϴ�.");
      System.out.println("����� �����Ͻ÷��� 1, ����� �ߵ� �߷��Ͻ÷��� 2�� �����ּ���.");

      if ( Integer.parseInt( sc.nextLine() ) == 2 ) return;

      contract.setPrice(finalPrice);
      System.out.println("�������� �Է��ϼ���. ( 0000-00-00 )");
      contract.setEndDate(sc.nextLine());

      int contractID = contractDao.create(contract);
      contract.setContractID(contractID);
      contractList.add(contract);

      System.out.println("����� ü��Ǿ����ϴ�.");
   }
   private static Customer contractCustomer(Insurance insurance, Customer customer) {
      System.out.println("���� ������� �Է��� �����մϴ�.");
      int accidentNum;
      String txtLine,buildingLine,carLine,driverLine;

      switch ( insurance.getInsuranceType()) {
      case "building":
         System.out.println("���� 3�� �̳� �ǹ�ȭ�� ��������� ������ ��� ���� Ƚ���� �Է����ּ���. ������ 0�� �Է����ּ���.");
         accidentNum = Integer.parseInt(sc.nextLine());

         for( int i = 0; i<accidentNum; i++ ) {
            System.out.println("�ǹ�ȭ�� ��¥/�ð�/���/��������(high,middle,high)�� �Է����ּ���. ");
            System.out.println("ex) 2021-05-23/18:30/����� ����/high");
            txtLine = sc.nextLine();
            Accident accident = new Accident();

            String[] txtSplit = txtLine.split("/");
            accident.setCustomer(customer);
            accident.setAccidentType("building");
            accident.setAccidentDate(txtSplit[0]);
            accident.setAccidentTime(txtSplit[1]);
            accident.setAccidentPlace(txtSplit[2]);
            accident.setAccidentSize(txtSplit[3]);
            accident.setAccidentComplete(0);
            
            int accidentID = accidentDao.create(accident);
            accident.setAccidentID(accidentID);
            customer.setAccident(accident);
            accidentList.add(accident);
         }
         Building building = new Building();
         System.out.println("����� �ǹ����� �Է��� �����մϴ�.");
         System.out.println("��� �Ͼ �ǹ��� ���࿬��/�ǹ�����(m����)/�ǹ�����/�ǹ�����/�ǹ��ܺ�(seramic,zinc,stone)�� �Է����ּ���. ");
         buildingLine = sc.nextLine();
         if ( !buildingLine.equals("") ) {
            String[] buildingSplit = buildingLine.split("/");
            building.setYear(Integer.parseInt(buildingSplit[0]));
            building.setBuildingHeight(Integer.parseInt(buildingSplit[1]));
            building.setBuildingPrice(buildingSplit[2]);
            building.setBuildingSize(Integer.parseInt(buildingSplit[3]));
            building.setBuildingOutwall(buildingSplit[4]);

            buildingDao.create(building, customer.getCustomerID());
            customer.setM_building(building);
         }
         break;

      case "car":
         System.out.println("���� 5�� �̳� �ڵ���ȭ�� ��������� ������ ��� ���� Ƚ���� �Է����ּ���. ������ 0�� �Է����ּ���.");
         accidentNum = Integer.parseInt(sc.nextLine());

         for( int i = 0; i<accidentNum; i++ ) {
            System.out.println("�ڵ���ȭ�� ��¥/�ð�/���/��������(high,middle,low)�� �Է����ּ���. ");
            System.out.println("ex) 2021-05-23/18:30/����� ����/high");
            txtLine = sc.nextLine();
            Accident accident = new Accident();

            String[] txtSplit = txtLine.split("/");
            accident.setCustomer(customer);
            accident.setAccidentDate(txtSplit[0]);
            accident.setAccidentType("car");
            accident.setAccidentTime(txtSplit[1]);
            accident.setAccidentPlace(txtSplit[2]);
            accident.setAccidentSize(txtSplit[3]);
            accident.setAccidentComplete(0);
            
            int accidentID = accidentDao.create(accident);
            accident.setAccidentID(accidentID);
            customer.setAccident(accident);
            accidentList.add(accident);
         }

         System.out.println("���� �ڵ������� �Է��� �����մϴ�.");
         System.out.println("�ڵ����� �ڵ�����ȣ/��������(��,��)/�ڵ�������(small,van,freightCar,motocycle,agricultureCar)�� �Է����ּ���. ");
         carLine = sc.nextLine();
         if ( !carLine.equals("") ) {
            Car car = new Car();
            String[] carSplit = carLine.split("/");
            car.setCarNum(carSplit[0]);
            if ( carSplit[1].equals("��") ) car.setOwn(1);
            else car.setOwn(0);

            car.setCarType(carSplit[2]);
            carDao.create(car, customer.getCustomerID());
            customer.setM_car(car);
         }
         break;

      case "driver":
         System.out.println("���� 2�� �̳� �ڵ��� ��������� ������ ��� ���� Ƚ���� �Է����ּ���. ������ 0�� �Է����ּ���.");
         accidentNum = Integer.parseInt(sc.nextLine());         

         for( int i = 0; i<accidentNum; i++ ) {
            System.out.println("�ڵ��� ��� ��¥/�ð�/���/��������(high,middle,low)�� �Է����ּ���. ");
            System.out.println("ex)2021-05-23/18:30/����� ����/high");
            txtLine = sc.nextLine();
            Accident accident = new Accident();

            String[] txtSplit = txtLine.split("/");
            accident.setCustomer(customer);
            accident.setAccidentType("driver");
            accident.setAccidentDate(txtSplit[0]);
            accident.setAccidentTime(txtSplit[1]);
            accident.setAccidentPlace(txtSplit[2]);
            accident.setAccidentSize(txtSplit[3]);
            accident.setAccidentComplete(0);
            
            int accidentID = accidentDao.create(accident);
            accident.setAccidentID(accidentID);
            customer.setAccident(accident);
            accidentList.add(accident);
         }

         System.out.println("���� �ڵ������� �Է��� �����մϴ�.");
         System.out.println("�ڵ����� �ڵ�����ȣ/��������(��,��)/�ڵ�������(small,van,freightCar,motocycle,agricultureCar)�� �Է����ּ���. ");
         carLine = sc.nextLine();
         if ( !carLine.equals("") ) {
            Car car = new Car();
            String[] carSplit = carLine.split("/");
            car.setCarNum(carSplit[0]);
            if ( carSplit[1].equals("��") ) car.setOwn(1);
            else car.setOwn(0);

            car.setCarType(carSplit[2]);
            carDao.create(car, customer.getCustomerID());
            customer.setM_car(car);
         }

         System.out.println("���� ������ ���� �Է��� �����մϴ�.");
         System.out.println("���� �����ڸ����ȣ/������������(personal/official)�� �Է����ּ���. ");
         driverLine = sc.nextLine();
         if ( !driverLine.equals("") ) {
            Driver driver = new Driver();
            String[] driverSplit = driverLine.split("/");
            driver.setLicenseNum(driverSplit[0]);
            driver.setCarPurpose(driverSplit[1]);

            driverDao.create(driver, customer.getCustomerID());
            customer.setM_driver(driver);
         }
         break;
      }
      return customer;
   }
   
   private static void contractManage() {
      System.out.println("****** ��� ����Ʈ ******");
      ArrayList<Contract> contracts = contractList.getContractList();
      for ( Contract contract : contracts ) { System.out.println(contract.getDate()+" "+contract.getContractID()+" "+contract.getCustomer().getCustomerName()+" "+contract.getInsurance().getInsuranceName()); }

      System.out.println("������ ����� ID�� �Է��ϼ���.");

      Contract contract = contractList.search(Integer.parseInt(sc.nextLine()));
      if ( contract == null ) {
         System.out.println("���� ���ID�� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
         contract = contractList.search(Integer.parseInt(sc.nextLine()));
      }

      System.out.println("***** ��� ���� *****");
      System.out.println("��೯¥ : " + contract.getDate());
      System.out.println("����� : " + contract.getInsurance().getInsuranceName());
      System.out.println("���� : " + contract.getCustomer().getCustomerName());
      System.out.println("�� ��ȭ��ȣ : " + contract.getCustomer().getPhoneNumber());
      System.out.println("���Ժ���� : " + contract.getPrice());
      System.out.println("������ : " + contract.getEndDate() + "\n");

      System.out.println("������ �޴��� �Է����ּ���.");
      System.out.println("1.��࿬�� 2.�űԻ�ǰü�� 3.����");

      switch(Integer.parseInt(sc.nextLine())) {
      case 1:
         System.out.println("1.1�⿬�� 2.2�⿬�� 3.3�⿬�� 4.���");
         int num = Integer.parseInt(sc.nextLine());
         while ( num > 4 ) {
            System.out.println( "�߸� �Է��ϼ̽��ϴ�. �ٽ� �޴��� �Է����ּ���." );
            num = Integer.parseInt(sc.nextLine());
         }
         if ( num == 4 ) return;

         String newEndDate = contract.getEndDate();
         
         try {
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            Calendar cal = Calendar.getInstance(); 
            
            Date dt = dtFormat.parse(contract.getEndDate());
            cal.setTime(dt);
            cal.add(Calendar.YEAR, num); 
            
            newEndDate =  dtFormat.format(cal.getTime());
         } catch (ParseException e) {
            e.printStackTrace();
         } 
         
         System.out.println("��� �������� "+contract.getEndDate()+"���� "+newEndDate+"�� �����մϴ�.");
         
         contract.setEndDate(newEndDate);
         
         contractDao.updateID(contract.getContractID(), contract);
         contractList.delete(contract.getContractID());
         contractList.add(contract);
         break;

      case 2: contract(contract.getCustomer().getCustomerID()); break;
      }
   }
   private static void customerManage() {
      System.out.println("****** ��� ����Ʈ ******");
      ArrayList<Contract> contracts = contractList.getContractList();
      for ( Contract contract : contracts ) { System.out.println(contract.getContractID()+" "+contract.getCustomer().getCustomerName()+" "+contract.getInsurance().getInsuranceName()); }

      System.out.println("������ ���� ��� ID�� �Է��ϼ���.");

      Contract contract = contractList.search(Integer.parseInt(sc.nextLine()));
      if ( contract == null ) {
         System.out.println("���� ���ID�� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
         contract = contractList.search(Integer.parseInt(sc.nextLine()));
      }

      System.out.println("***** ��� ���� *****");
      System.out.println("��೯¥ : " + contract.getDate());
      System.out.println("����� : " + contract.getInsurance().getInsuranceName());
      System.out.println("���� : " + contract.getCustomer().getCustomerName());
      System.out.println("�� ��ȭ��ȣ : " + contract.getCustomer().getPhoneNumber());
      System.out.println("���� : " + contract.getCustomer().getJob());
      System.out.println("�ּ� : " + contract.getCustomer().getAddress());
      System.out.println("�ֹι�ȣ : " + contract.getCustomer().getCustomerNumber());
      System.out.println("���Ժ���� : " + contract.getPrice());
      System.out.println("����� ���� Ƚ�� : " + accidentDao.retrieveAccidentCnt(contract.getCustomer().getCustomerID()));

      System.out.println("������ : " + contract.getEndDate() + "\n");

      System.out.println("�޴��� �������ּ���.");
      // ������Ʈ ����� ���� DB�� ���� ���� �ʱ� ������ ���ݽ��ϴ�.
      System.out.println("1.���������� 3.����");

      switch ( Integer.parseInt(sc.nextLine()) ) {
      case 1:
         System.out.println("***** �� ���� ������ �����մϴ�. *****");
         System.out.println("������ �� �̸��� �Է����ּ���.");
         String newName = sc.nextLine();
         if ( newName != "" ) contract.getCustomer().setCustomerName(newName);
         System.out.println("������ �� ��ȭ��ȣ�� �Է����ּ���.");
         String newPhone = sc.nextLine();
         if ( newPhone != "" ) contract.getCustomer().setPhoneNumber(newPhone);
         System.out.println("������ �� ������ �Է����ּ���.");
         String newJob = sc.nextLine();
         if ( newJob != "" ) contract.getCustomer().setJob(newJob);
         System.out.println("������ �� �ּҸ� �Է����ּ���.");
         String newAddress = sc.nextLine();
         if ( newAddress != "" ) contract.getCustomer().setAddress(newAddress);

         contractDao.updateIDCustomer(contract);
         contractList.delete(contract.getContractID());
         contractList.add(contract);
         System.out.println("�� ���� ������ �Ϸ�Ǿ����ϴ�. �ʱ� ȭ������ ���ư��ϴ�.");

         break;

      case 3:
         break;
      }
   }
   // ����
   private static void checkcustomerInfo() {
      // �� ���� Ȯ���ϱ�
      System.out.println("***** �������� �Է����ּ���. *****");
      Customer customer = new Customer();
      System.out.println("�̸� : ");
      customer.setCustomerName(sc.nextLine());
      System.out.println("�ֹι�ȣ(-����) : ");
      customer.setCustomerNumber(sc.nextLine());
      
      if(customer.getCustomerName() != null && customer.getCustomerNumber() != null) { // ����帧 1
         System.out.println("***** �Էµ� ���� �����Դϴ�*****");
         System.out.println("�̸� : " + customer.getCustomerName() + "\n" + "�ֹι�ȣ : " + customer.getCustomerNumber());
         System.out.println("���� ������ Ȯ���Ͻðڽ��ϱ�? Ȯ���Ͻ÷��� 1��, ����Ͻ÷��� 2�� �����ּ���.");
         
         switch ( Integer.parseInt(sc.nextLine())) {
         case 1:
            Customer bcustomer = customerList.search(customer);
            if( bcustomer != null) { // ������Ʈ�� ���� 
               System.out.println("ID : " + bcustomer.getCustomerID() + "\n" + 
                     "�̸� : " + bcustomer.getCustomerName() + "\n" 
                     + "�ֹι�ȣ : " + bcustomer.getCustomerNumber() + "\n" + 
                     "���� : " + bcustomer.getSex() + "\n"
                     + "��ȭ��ȣ: " + bcustomer.getPhoneNumber());
               
               System.out.println("�ش� ���� ���� ���� ���θ� Ȯ���մϴ�.");
               
               Contract contract = contractList.searchByCustomer( bcustomer.getCustomerID() );
               if ( contract == null ) {
                  System.out.println("���� ����� �Ǿ� ���� ���� ���� ��� ������ �Ұ����մϴ�.");
                  
                  return;
               }
               else {
                  System.out.println("���� ��������� Ȯ���մϴ�.");
                  
                  System.out.println("***** ��� ���� *****");
                  System.out.println("��೯¥ : " + contract.getDate());
                  System.out.println("����� : " + contract.getInsurance().getInsuranceName());
                  System.out.println("���� : " + contract.getCustomer().getCustomerName());
                  System.out.println("�� ��ȭ��ȣ : " + contract.getCustomer().getPhoneNumber());
                  System.out.println("���� : " + contract.getCustomer().getJob());
                  System.out.println("�ּ� : " + contract.getCustomer().getAddress());
                  System.out.println("�ֹι�ȣ : " + contract.getCustomer().getCustomerNumber());
                  System.out.println("���Ժ���� : " + contract.getPrice());
                  System.out.println("����� ���� Ƚ�� : " + accidentDao.retrieveAccidentCnt(contract.getCustomer().getCustomerID()));

                  System.out.println("������ : " + contract.getEndDate() + "\n");
                  
                  System.out.println("��� ������ ��� �����Ͻ÷��� 1��, ����Ͻ÷��� 2�� �Է����ּ���.");
                  if ( Integer.parseInt(sc.nextLine()) == 2 ) return;
               }
               System.out.println("��� �����ϴ� �������� �̵��մϴ�");
               acceptAccident(bcustomer);
            } else {System.out.println("���� �� �����Դϴ�. ���� �޴� ó������ ���ư��ϴ�.");}
            break;
            
         case 2:
            System.out.println("����ϼ̽��ϴ�. �ʱ� �޴��� ���ư��ϴ�.");
            break;
         }
      } 
      else System.out.println("���Էµ� ���� �����մϴ�. �ʱ� �޴��� ���ư��ϴ�.");
   }      
   private static void acceptAccident(Customer bcustomer) {// ��� ����
      Accident accident = new Accident();
      if(bcustomer == null) {
         System.out.println("----------- ��� �����ϴ� ���� ���� Ȯ�����ּ���. �� ���� Ȯ�� �޴��� �̵��մϴ�. ------------------");
         checkcustomerInfo(); // ������Ȯ��
      } else {
      accident.setCustomer(bcustomer);
      System.out.println();
      System.out.println("***** ��������� �Է����ּ���. *****");
      System.out.println("��� ��¥(yyyy-mm-dd)�� �Է����ּ���.");
      accident.setAccidentDate(sc.nextLine());
      if(accident.getAccidentDate().length() != 10) { // ��� �帧 2
         System.out.println("������ ��Ŀ� �°� �Է��ϼž� �մϴ�. ��� ��¥(yyyy-mm-dd)");
         accident.setAccidentDate(sc.nextLine());
      }
      if(dateCalculate(accident.getAccidentDate())) { // ��� �帧 1
         System.out.println("��� �ð��� �Է����ּ���.");
         accident.setAccidentTime(sc.nextLine());
         System.out.println("��� ��Ҹ� �Է����ּ���.");
         accident.setAccidentPlace(sc.nextLine());
         System.out.println("��� ����(building, car, driver)�� �Է����ּ���.");
         accident.setAccidentType(sc.nextLine());
         System.out.println("��� �ܰ�(��, ��, ��)�� �Է����ּ���.");
         accident.setAccidentSize(sc.nextLine());
         if(accident.getAccidentDate() != "" && accident.getAccidentTime() != "" 
               && accident.getAccidentPlace() != "" && accident.getAccidentType() != null && accident.getAccidentSize() !=null) {
            System.out.println("��� ��¥: " + accident.getAccidentDate() + "\n" 
                  + "��� �ð�: " + accident.getAccidentTime() + "\n" + 
                  "��� ��� : " + accident.getAccidentPlace() + "\n"
                  + "��� ����: " + accident.getAccidentType() + "\n" +
                  "���ܰ� : " + accident.getAccidentSize());
            System.out.println("��� ������ �����մϴ�. 1.�� 2. �ƴϿ�");
            int flagNum = Integer.parseInt(sc.nextLine());
            if(flagNum == 1) {
               int accidentID = accidentDao.create(accident);
               accident.setAccidentID(accidentID);
               accident.setAccidentComplete(1); // ��� ��ó��
               accident.setJudgementComplete(1); // ���å ��ó��
               SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
               Date time = new Date();
               accident.setDate(format.format(time));
               accidentDao.createInfo(accident);
               accidentList.add(accident);
               System.out.println("��� ������ �Ϸ�Ǿ����ϴ�.");
            } else if(flagNum == 2) { // ��� �帧 4
               System.out.println("��� ������ ����ϼ̽��ϴ�.");
            } 
         } else { // ��� �帧 3
            System.out.println("���Էµ� ���� �����մϴ�. ���� �޴� ó������ ���ư��ϴ�.");
         }
      } else System.out.println("1�� �Ѱ� ���� ����� ������ ������ �� �����ϴ�. ���� �޴� ó������ ���ư��ϴ�.");
      }
   }
   private static void investigate() { // ������������ϱ�
      System.out.println("�۾��� ���� ��ȣ�� �Է��ϼ���. 1.���ó���ϱ� 2.������� �����ϱ�");
      int flagNum = Integer.parseInt(sc.nextLine());
      if(flagNum == 1) { addAccidentInfo();    }
      else if(flagNum == 2) { // ����帧
         checkcustomerInfo();
         addAccidentInfo();
      }
   } 
   private static void addAccidentInfo() {
      System.out.println("**** ������ ��� ����Ʈ ****");
      ArrayList<Accident> accidents = accidentDao.retrieveNotcompleted().getAccidentList();
      if(accidents.size() != 0) { // ��� ��� ó���Ǿ��� ���
         for(Accident sAccident : accidents) {
            System.out.println("��� ��ȣ: " + sAccident.getAccidentID() + "\n" + 
                  "��� ��¥: " + sAccident.getAccidentDate() + "\n" 
                  + "��� �ð�: " + sAccident.getAccidentTime() + "\n" + 
                  "��� ��� : " + sAccident.getAccidentPlace() + "\n"
                  + "��� ����: " + sAccident.getAccidentType() + "\n" +
                  "���ܰ� : " + sAccident.getAccidentSize() + "\n" );
            System.out.println("---------------------------------------");
         }
         System.out.println("ó���� ����� ID�� �Է��ϼ���."); // �⺻�帧
         Accident daccident = accidentList.search(Integer.parseInt(sc.nextLine()));
         while ( daccident == null ) {
            System.out.println("�������� �ʴ� ��� ID�� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
            daccident = accidentList.search(Integer.parseInt(sc.nextLine()));
         }
         System.out.println("��� ��ȣ: " + daccident.getAccidentID() + "\n" + 
               "��� ��¥ : " + daccident.getAccidentDate() + "\n" 
               + "��� �ð�: " + daccident.getAccidentTime() + "\n" + 
               "��� ��� : " + daccident.getAccidentPlace() + "\n"
               + "��� ����: " + daccident.getAccidentType() + "\n" +
               "���ܰ� : " + daccident.getAccidentSize() + "\n" );
         System.out.println("******** ���� ������ �߰����ּ���. ************");
         System.out.println("��� �ó������� �Է��ϼ���.");
         daccident.getM_siteInfo().setScenario(sc.nextLine());
         
         System.out.println("��� ����� ������ ������ 1, ������ 2�� �Է��ϼ���.");
         daccident.getM_siteInfo().setRecord(Integer.parseInt(sc.nextLine()));
         
         System.out.println("������ ������ 1, ������ 2�� �Է��ϼ���.");
         daccident.getM_siteInfo().setVideo(Integer.parseInt(sc.nextLine()));
         
         System.out.println("������ ������ 1, ������ 2�� �Է��ϼ���.");
         daccident.getM_siteInfo().setPhoto(Integer.parseInt(sc.nextLine()));
         
         System.out.println("���� �Ը�(��, ��, ��)�� �Է��ϼ���.");
         daccident.getM_siteInfo().setDamageScale(sc.nextLine());
         
         System.out.println("�ش� ����� �ý��ۿ� ����Ͻ÷��� 1�� �Է��ϼ���.");
         if(Integer.parseInt(sc.nextLine()) == 1) {
            daccident.setAccidentComplete(0); // ��� ó��
            accidentDao.createInvestigation(daccident);
            accidentDao.update(daccident);
         }
      } else System.out.println("ó���� ��� �����ϴ�.");
   }
   private static void decideExemption() {
      // ��/��å �Ǵ��ϱ�
      System.out.println("******* ��/��å ����Ʈ ********");
      ArrayList<Accident> accidents = accidentDao.retrievecommpleted().getAccidentList();
      if( accidents != null ) {
         for(Accident accident : accidents) {
               System.out.println("��� ��ȣ: " + accident.getAccidentID() + "\n" + 
                     "��� ���� ��¥ : " + accident.getDate() + "\n" +
                     "��� �ܰ� : " + accident.getAccidentSize() + "\n" + 
                     "�ɻ翩�� ( 0 �ɻ�, 1 �̽ɻ�) : " + accident.getJudgementComplete());
               System.out.println("------------------------------------");
               if(accident.getDate() == "" || accident.getAccidentSize() == null ) { // �����帧 1
                  System.out.println("�ش� ������ ������ �־� �ɻ��� �� �����ϴ�. ���� �޴� �ʱ�� ���ư��ϴ�.");
                  dataflag = true;
            }
         }
      }
      if(!dataflag) { // �����帧 flag
         System.out.println(" ��/��å �ɻ��� ����� ID�� �Է��ϼ���.");
         Accident accident =  accidentList.search(Integer.parseInt(sc.nextLine()));
         System.out.println("-------------------------------------");
         System.out.println("��� ��ȣ: " + accident.getAccidentID() + "\n" + 
               "��� ��¥ : " + accident.getAccidentDate() + "\n" +
               "��� ��� : " + accident.getAccidentPlace() + "\n"
               + "��� ���� : " + accident.getAccidentType() + "\n" +
               "��� �ܰ� (��, ��, ��) : " + accident.getAccidentSize() + "\n" + 
               "��� �ó����� : " + accident.getM_siteInfo().getScenario() + "\n" + 
               "��� �����(1 ����, 2 ����): " + accident.getM_siteInfo().getRecord() + "\n" + 
               "����(1 ����, 2 ����): " + accident.getM_siteInfo().getVideo() + "\n" + 
               "����(1 ����, 2 ����): " + accident.getM_siteInfo().getPhoto() + "\n" +
               "���رԸ�(��, ��, ��) : " + accident.getM_siteInfo().getDamageScale());
         System.out.println("-------------------------------------");
         System.out.println("��/��å ������ �Է����ּ���.");
         Exemption exemption = new Exemption();  // ��/��å �� ������
         System.out.println("��/��å ����(����, ����)�� �Է��ϼ���.");
         String isaccepted = sc.nextLine();
         if(isaccepted.equals("����")) {accident.setJudgementComplete(0);}
         else if(isaccepted.equals("����")) {accident.setJudgementComplete(1);}
         System.out.println("�Ǵ� ������ �ۼ��ϼ���.");
         exemption.setReason(sc.nextLine());
         System.out.println("���� �ڷᰡ �ֳ���? ������ 1, ������ 2�� �Է��ϼ���.");
         exemption.setSubFile(Integer.parseInt(sc.nextLine()));
         System.out.println("���� ���� ������ �Է��ϼ���.");
         exemption.setLegacy( sc.nextLine());
         if( exemption.getReason() != "" && exemption.getSubFile() != 0 && exemption.getLegacy() != "") { // ��� �帧 1
            exemption.setAccidentID(accident.getAccidentID()); // ��� ID ����
            exemptionDao.create(exemption);
            accidentDao.updateJudged(accident);
            System.out.println("�ش� ����� ��/��å �ɻ簡 �Ϸ�Ǿ����ϴ�.");
         } else {
            System.out.println("���Էµ� ������ �����մϴ�. ���� �޴� �ʱ�� ���ư��ϴ�.");
         }
      }
   }
   private static void damageAssessment() {
      System.out.println("**********���ػ��� ��⸮��Ʈ*********");
      ArrayList<Exemption> damageassessmentList = exemptionDao.retrieveList().getExemptionList(); // ���ε� ����Ʈ��
      for(Exemption sAccident : damageassessmentList) { 
            System.out.println("��� ��ȣ : "+ sAccident.getAccidentID());
            System.out.println("���߻� ��¥ : "+ sAccident.getAccidentDate());
            System.out.println("------------------------------------");
      }
      System.out.println("���ظ� ������ ����� ID�� �Է��ϼ���");
      Exemption exemption = exemptionDao.retrieveList().search(Integer.parseInt(sc.nextLine()));
      System.out.println("-------------------------------------");
      System.out.println("��� ��ȣ: " + exemption.getAccidentID() + "\n" + 
            "�Ǵ� ����: " + exemption.getReason() + "\n" +
            "���� �ڷ�( 1 ����, 2 ����) : " + exemption.getSubFile() + "\n" +
            "���� ����: " + exemption.getLegacy() + "\n" +
            "�� ��ȣ: " + exemption.getCustomerID());
      System.out.println("-------------------------------------");
      System.out.println("���ػ��� ������ �Է��ϰڽ��ϴ�.");
      RewardInfo rewardInfo = new RewardInfo();
      rewardInfo.setEmployee(employee);;
      System.out.println("���� �ݾ��� �Է����ּ���");
      rewardInfo.setPayment(sc.nextLine());
      System.out.println("�Ǵ� ������ �ۼ����ּ���");
      rewardInfo.setAssessReason(sc.nextLine());
      Customer customer = customerList.search(exemption.getCustomerID()); 
      System.out.println();
      System.out.println(customer.getCustomerName() + "�Բ� "+ "���ػ��� ��� ��������� " +rewardInfo.getPayment() + "���� �����մϴ�.");
      rewardInfo.setAccident(exemption);
      damageDao.create(rewardInfo); //���ػ��� ���� ��� ����
      accidentList.delete(exemption.getAccidentID());
   }
   private static int calcualteCustomerAge(Customer customer) {
      String[] strArrary = customer.getCustomerNumber().split("");
      int ageYear;
      if(Integer.parseInt(strArrary[0])!=0) ageYear = 1900 + Integer.parseInt(strArrary[0])* 10 + Integer.parseInt(strArrary[1]);
      else ageYear = 2000 + Integer.parseInt(strArrary[1]);
      GregorianCalendar today = new GregorianCalendar();
      int year = today.get(Calendar.YEAR);
      return year - ageYear;
   }
   private static boolean dateCalculate(String accidentDate) { 
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String endDate = df.format(cal.getTime());
      cal.add(Calendar.YEAR, -1);
      String startDate = df.format(cal.getTime());
      LocalDate localDate = LocalDate.parse(accidentDate);
      LocalDate startLocalDate = LocalDate.parse(startDate);
      LocalDate endLocalDate = LocalDate.parse(endDate);
      endLocalDate = endLocalDate.plusDays(1);
      return (! localDate.isBefore(startLocalDate)) && (localDate.isBefore(endLocalDate));
   }
}