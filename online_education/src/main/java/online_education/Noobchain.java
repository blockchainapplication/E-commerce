package online_education;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Noobchain {
	public static ArrayList<Block> blockchain = new ArrayList();
	public static int difficulty = 1;
	static List<Course> courses = new ArrayList<Course>();
	static List<Teacher> teachers = new ArrayList<Teacher>();
	static List<Student> students = new ArrayList<Student>();
	static Map<String, LearningRecord> learningRecords = new HashMap<String, LearningRecord>();

	public static void main(String[] args) throws IOException {
		URL url = new URL("https://ke.qq.com/course/list/%E6%B1%89%E8%AF%AD");
		InputStream in = url.openStream();
		InputStreamReader isr = new InputStreamReader(in, "utf-8");
		BufferedReader content = new BufferedReader(isr);
		StringBuilder stringBuilder  = new  StringBuilder  () ;
        String html;
        while ((html = content.readLine()) != null) {
        	stringBuilder.append(html);
        }
        content.close();
        isr.close();
        in.close();
		Document doc = Jsoup.parse(stringBuilder.toString());
		Elements course_card_item = doc.getElementsByClass("course-card-item");
		Teacher t1 = new Teacher();
		t1.settName("蔡徐坤老师");
		teachers.add(t1);
		for (Element element : course_card_item) {
			
			Course Course = new Course();
			Elements pictitles = element.getElementsByClass("item-tt-link");
			String title = pictitles.attr("title");
			System.out.println(title.toString());
			Course.setPictitles(title);
			ThreadLocalRandom threadLocalRandom =ThreadLocalRandom.current();
			Course.setLesson(threadLocalRandom.nextInt(10,19));
			Course.setTeacher(t1);
			courses.add(Course);
		}

		Student s1 = new Student();
		s1.setsName("Tony Stark");
		s1.setClasss("复联2班");
		students.add(s1);
		Student s2 = new Student();
		s2.setsName(" Steve Rogers");
		s2.setClasss("复联2班");
		students.add(s2);
		Student s3 = new Student();
		s3.setsName("Natasha Romanoff");
		s3.setClasss("复联2班");
		students.add(s3);

		ManagementOffice managementOffice = new ManagementOffice();
		managementOffice.setName("学习监督管理处");
		Teacher t2 = new Teacher();
		t2.settName("灭霸老师");
		managementOffice.setTeacher(t2);
		// 开始报名学习
		for ( int s = 0 ; s<students.size();s++) {
			Student student = students.get(s);
			Course course = courses.get(s);
			System.out.println("学生: " + student.getsName() + " 报名并开始学习 	" + course.getLesson());
			List<Course> courses = new ArrayList<Course>();
			courses.add(course);
			student.setCourses(courses);
			// 记录学习情况
			LearningRecord learningRecord = new LearningRecord();
			learningRecord.setCourse(course);
			learningRecord.setStudent(student);
			learningRecord.setProgress(1);
			learningRecords.put(student.getsName(), learningRecord);
		}
		// 进行课程学习上课
		
		for (String studenName : learningRecords.keySet()) {
			LearningRecord learningRecord = learningRecords.get(studenName);
			Student student = learningRecord.getStudent();
			Course course = learningRecord.getCourse();
			Teacher teacher = course.getTeacher();
			int sectionNumber = 0;
			for (int y = 0; y < 15; y++) {
				System.out.println(
						"课程: " + course.getPictitles() + " 开始上第" + sectionNumber++ + "课, 主讲老师:" + teacher.gettName());
				System.out.println("学生: "+student.getsName()+"学习ing . . . . .  .");

			}
			learningRecord.setProgress(sectionNumber);
			learningRecords.put(student.getsName(), learningRecord);

		}
		// 学习成果
		for (String studenName : learningRecords.keySet()) {
			LearningRecord learningRecord = learningRecords.get(studenName);
			Course course = learningRecord.getCourse();
			Student student = learningRecord.getStudent();
			int progress = learningRecord.getProgress();
			int lesson = course.getLesson();
			int number = lesson - progress;
			System.out.println(
					"学生:" + student.getsName() + "报名参加学习: " + course.getPictitles() + "课程期间   " + "旷课:" + number + "次");
			if (number > 0) {
				System.out.println("经" + managementOffice.getName() + "		" + managementOffice.getTeacher().gettName()
						+ "审核,给予不通过");
			} else {
				System.out.println("经" + managementOffice.getName() + "		" + managementOffice.getTeacher().gettName()
						+ "审核,学习成果认证");
			}
		}
		Gson gson = new Gson();
		String learningRecordsJson = gson.toJson(learningRecords);
		blockchain.add(new Block(learningRecordsJson, "0")); // 创建区块
		System.out.println("Trying to Mine block 1... ");
		blockchain.get(0).mineBlock(difficulty);// 尝试进行挖矿,然后创建第二个区块,依次类推

		blockchain.add(new Block(learningRecordsJson, blockchain.get(blockchain.size() - 1).hash));
		System.out.println("Trying to Mine block " + (blockchain.size()) + "......");
		blockchain.get(blockchain.size() - 1).mineBlock(difficulty);

		blockchain.add(new Block(learningRecordsJson, blockchain.get(blockchain.size() - 1).hash));
		System.out.println("Trying to Mine block " + (blockchain.size()) + "......");
		blockchain.get(blockchain.size() - 1).mineBlock(difficulty);
		System.out.println("\nBlockchain is Valid: " + isChainValid());

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
		System.out.println("\nStart crawling data......");

	}

	public static Boolean isChainValid() {
		String hashTarget = new String(new char[difficulty]).replace('\000', '0');
		for (int i = 1; i < blockchain.size(); i++) {
			Block currentBlock = (Block) blockchain.get(i);
			Block preciousBlock = (Block) blockchain.get(i - 1);
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return Boolean.valueOf(false);
			}
			if (!preciousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes not equal");
				return Boolean.valueOf(false);
			}
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return Boolean.valueOf(false);
			}
		}
		return Boolean.valueOf(true);
	}
}
