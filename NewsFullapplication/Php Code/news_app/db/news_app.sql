-- phpMyAdmin SQL Dump
-- version 4.1.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 28, 2014 at 02:10 PM
-- Server version: 5.1.73-cll
-- PHP Version: 5.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `viaviweb_news_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `app_verify`
--

CREATE TABLE IF NOT EXISTS `app_verify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buyer` varchar(255) NOT NULL,
  `purchase_code` varchar(255) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `app_verify`
--

INSERT INTO `app_verify` (`id`, `buyer`, `purchase_code`, `status`) VALUES
(1, '', '', 0);



--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(255) CHARACTER SET latin1 NOT NULL,
  `password` varchar(255) CHARACTER SET latin1 NOT NULL,
  `email` varchar(255) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`, `email`) VALUES
(1, 'admin', '3971e14fc433d2bb3103940e07046880', 'viaviwebtech@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_news`
--

CREATE TABLE IF NOT EXISTS `tbl_news` (
  `nid` int(11) NOT NULL AUTO_INCREMENT,
  `cat_id` int(11) NOT NULL,
  `news_heading` varchar(500) NOT NULL,
  `news_description` text NOT NULL,
  `news_image` varchar(255) NOT NULL,
  `news_date` varchar(255) NOT NULL,
  `news_status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`nid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=20 ;

--
-- Dumping data for table `tbl_news`
--

INSERT INTO `tbl_news` (`nid`, `cat_id`, `news_heading`, `news_description`, `news_image`, `news_date`, `news_status`) VALUES
(8, 7, 'Gaza bloodshed spirals as US offers to broker truce', '<p>Gaza Strip: Israeli warplanes kept up their bombing of Gaza on Friday as Hamas rockets set sirens wailing, raising fears the conflict could spread as Washington offered help brokering a truce.As the bloodiest battle between Israel and Hamas in 20 months escalated on its fourth day, diplomatic efforts to end the hostilities gathered pace.&nbsp;US President Barack Obama phoned Israeli Prime Minister Benjamin Netanyahu to express concerns and to offer his help in resolving the crisis.&quot;The United States remains prepared to facilitate a cessation of hostilities, including a return to the November 2012 ceasefire agreement,&quot; the White House said, referring to a truce which ended the last major confrontation between Israel and militants from the Islamist Hamas movement in Gaza.&nbsp;</p>\r\n', '72384_gaza.jpg', '07-11-2014', 1),
(9, 7, 'Militants shell eastern Ukraine airport', '<p>London: Pro-Russian militants shelled eastern Ukrainian city Donetsk&#39;s airport, a media report said Friday.&nbsp;<br />\r\nRebel and government forces have confirmed that Donetsk airport came under fire late Thursday, BBC reported.<br />\r\nAccording to witnesses, explosions and machine-gun fire could be heard near the airport, and they saw rockets launched towards it.The pro-Russian militants said they were continuing an offensive on the airport, which has been held by Ukrainian government forces after it was briefly captured by the rebels in May.&nbsp;</p>\r\n', '72163_israel.jpg', '07-11-2014', 1),
(10, 7, 'Israel presses on with Gaza offensive, Palestinians fire rockets', '<p>Gaza/Jerusalem: Israel pressed on for a fourth day on Friday with its Gaza offensive, striking the Hamas-dominated enclave from air and sea, as Palestinian militants kept up rocket attacks deep into the Jewish state.&nbsp;<br />\r\nAt least 79 Palestinians, most of them civilians, have been killed in the offensive, which Israel says it launched to end persistent rocket attacks on its civilian population, some of which have reached Tel Aviv, Jerusalem and other cities.&nbsp;<br />\r\nIsraeli leaders have appeared to hint at a possible invasion by ground forces and some 20,000 army reservists have been mobilised, giving them the means, if they choose, to mount a land offensive.<br />\r\nGaza medical officials said four people were killed in Israeli pre-dawn attacks. The Israeli military said fresh naval and air strikes were launched early, but a spokeswoman gave no further details</p>\r\n', '10608_gaza.jpg', '07-11-2014', 1),
(11, 4, 'Sunil Gavaskar was as good as Sachin Tendulkar, Don Bradman: Bapu Nadkarni', '<p><code>Mumbai: The modern generation of cricket followers might feel Sachin Tendulkar is the last word in batting, but for people of the previous generation or even older, Sunil Gavaskar was as good a batsman, according to former Test all-rounder Bapu Nadkarni.<br />\r\n&quot;While it is Tendulkar now, then it was Gavaskar. In the class of cricket (batting) Gavaskar was as good as Tendulkar as a stroke maker, or even the Don (Bradman),&quot; said the octogenarian in his keynote address at the Legends Club here last evening at the function to celebrate Gavaskar`s 65th birthday.</code></p>\r\n\r\n<pre>\r\n\r\n&nbsp;</pre>\r\n', '82082_sach.jpg', '07-11-2014', 1),
(12, 4, '\\''Weak link\\'' Hoewedes out to keep Messi quiet', '<p>Santo Andre, Brazil: The defender branded Germany`s `weakest link` says he is ready for the toughest job in football: keeping Lionel Messi quiet in Sunday`s World Cup final.<br />\r\nBenedikt Hoewedes has forced his way into Joachim Loew`s starting line-up after Germany arrived at Brazil 2014 without an established left-back.<br />\r\nNow he will run out at Rio de Janeiro`s iconic Maracana Stadium to face Messi and Argentina for the world title.<br />\r\nHaving kept the Selecao in check in the 7-1 semi-final thrashing of Brazil, Hoewedes` next task will be helping to shackle Messi.<br />\r\nHoewedes says the Germany defence will pay Argentina`s superstar the same close attention Cristiano Ronaldo received in the 4-0 rout of Portugal in the group stages.</p>\r\n', '59672_benedikt.jpg', '07-11-2014', 1),
(13, 4, 'Michael Schumacher\\''s wife believes he\\''s getting better: magazine', '<p>Berlin: The wife of Michael Schumacher believes the former Formula One champion is getting better after emerging from his coma and being moved to a Swiss hospital last month, she told a German magazine.<br />\r\nIn her first public comments since Schumacher`s horrific ski accident on December 29, 2013, Corinna Schumacher told Neue Post that the improvement in the health of her famous husband was encouraging.</p>\r\n', '44582_schuu.jpg', '07-11-2014', 1),
(14, 5, ' Story Sensex, Nifty plunge on profittaking; Infosys rises', '<p>The benchmark BSE Sensex plunged 348 points as funds and retail investors indulged in profit-booking, amid weak global cues.After rising to 25,548.33 points in morning trade on the back of better-than-expected Q1 earnings by Infosys, the Sensex fell to trade lower by 197.61 points, or 0.78 per cent, to 25,175.14 at 1230 hours.The index closed 1.37 per cent lower at 25,024 points.&nbsp;The broader Nifty fell 1.4 per cent to post its biggest weekly loss since March 2013 as blue chips such as Reliance Industries were hit by a range of factors including profit-taking and disappointment over the Budget&#39;s lack of specifics.Reliance Industries fell 3.1 percent while Larsen &amp; Toubro lost 4.8 percent on the Nifty.Among Sensex heavyweights, the Reliance Industries stock fell 3.13 per cent or 31 points to 967 level.The 30-share index had lost 727.33 points in the previous three sessions.</p>\r\n', '94167_bse.jpg', '07-11-2014', 1),
(15, 5, 'Except new IITs, IIMs, the Budget does not offer much', '<p>Though education, skill development and employment were the key issues in the election manifesto of the BJP, the Budget failed to give a precious little in terms of direction or money allocated. It failed to create an incrementally different roadmap. The Twitterati was quick to see this. Rs 200 crore for the largest statue of Sardar Vallabhbhai Patel and only Rs 100 crore for the Beti Bachao, Beti Padhao Yojna.</p>\r\n', '3471_iim.jpg', '07-11-2014', 1),
(16, 5, 'Infosys attrition rate jumps to 19.5% in Q1, concerns analysts', '<p>Infosys, India&#39;s third-largest IT exporter, opened the technology earnings calendar for the April-June quarter by reporting numbers that were in line with market expectations.<br />\r\nThe company reported a net profit of Rs 2,886 crore for the quarter, or Rs 50.51 a share, down 3.5 per cent from Rs 2,992 crore, or Rs 52.36 a share, in the previous quarter. Dollar revenues inched up two per cent to $2.13 billion.<br />\r\nThe company reported a stable business environment, added 61 clients and reported momentum in large deal wins during the quarter.<br />\r\nBut its high attrition rate concerned analysts.<br />\r\nInfosys&#39;s attrition rate shot up to 19.5 per cent in the June quarter from 18.7 per cent in March. A year ago, the attrition rate was 16.9 per cent. The company had 161,284 employees as on June 30 - it added just 879 employees on a net basis since March.</p>\r\n', '61036_infosys.jpg', '07-11-2014', 1),
(17, 6, '  Batman celebrates 75th birthday in Hollywood style!', '<p>Ker-pow! Batman is celebrating his 75th birthday this month with a series of comic-book, video-game and other events putting Gotham&#39;s famed Caped Crusader firmly back in the spotlight.<br />\r\nJuly 23 is officially Batman Day, and to mark the occasion in Hollywood, Warner Bros has organised a VIP studio tour including Batmobiles, masks, capes and other souvenirs of the Dark Knight.<br />\r\n&quot;The world has no heroes... Batman gives you some hope and some faith,&quot; actor Danny DeVito, who played the caped one&#39;s nemesis, the Penguin, in the 1992 movie Batman Returns, said in an interview.<br />\r\nBatman was born in May 1939, the creation of artist Bob Kane and writer Bill Finger, who were tasked by DC Comics with creating a new superhero in the wake of the success of Superman.<br />\r\nUnlike Superman, the new hero was given a dark side, and rapidly became a global pop culture icon, decades before the latest generation of superheroes like Captain America, Spiderman or Iron Man took to the big screen.<br />\r\nWhile Superman represents the archetypal hero with super-human powers, a brightly coloured costume and appears in daylight, Bruce Wayne is a wealthy tycoon who changes into a mask and dark cape to bring justice to Gotham City.<br />\r\n&nbsp;</p>\r\n', '40817_batmanfeatur.jpg', '07-11-2014', 1),
(18, 6, 'Dad has promised to write a script for me: Alia Bhatt', '<p>Riding high on the success of her previous release, in which she was seen playing a Tamilian, actor Alia Bhatt says her father and ace filmmaker Mahesh Bhatt will write a script for her in the near future.<br />\r\n&ldquo;He (Mahesh Bhatt) is not going to direct, but will definitely write a script for me, which will be an award-winning performance. He has promised me and it will happen,&rdquo; says the 21-year-old actor, who is busy promoting her next film. Alia, who has earlier sung in Imtiaz Ali&rsquo;s Highway (2013), has done playback singing for this upcoming rom com as well. She has sung the unplugged version of the song, Main Tennu Samjhawaan.<br />\r\nMeanwhile, Bhatt is reported to have said that he had tears in his eyes when he first heard the song.&ldquo;Usually, I don&rsquo;t attend her (Alia&rsquo;s) events. But I came here, because she had sent me the song two days back and asked for my opinion. I had tears in my eyes when I heard it.She has sung it from heart,&rdquo; he said during the unveiling of the song at a recent event.<br />\r\n&nbsp;</p>\r\n', '50327_latest_news.jpg', '07-11-2014', 1),
(19, 6, ' Zohra\\''s loss leaves film fraternity heartbroken', '<p>The death of legendary actress Zohra Sehgal here on Thursday left the film industry mourning in extreme grief.<br />\r\nNo sooner had the news of her demise was out, celebrities from Bollywood and friends and admirers of the livewire personality took to social media to pay their tributes to her.<br />\r\nActors, filmmakers, among others shifted between nostalgia and melancholia, in recalling her extraordinary career as actor and choreographer, the twin hats that she wore with equal aplomb.<br />\r\n&quot;Zohra Sehgal passes away at 102 yrs... What a journey and what an immensely lovable co-star! Prayer for her blessed soul!,&quot; actor Amitabh Bachchan tweeted.<br />\r\nFilmmaker Madhur Bhandarkar paid his homage by sharing a vintage picture of her from the 1940s on the micro-blogging site.<br />\r\n&quot;Zohra Sehgal, during a stage performance...&quot; he said on his account.<br />\r\n&nbsp;</p>\r\n', '73896_latest_news.jpg', '07-11-2014', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_news_category`
--

CREATE TABLE IF NOT EXISTS `tbl_news_category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `category_image` varchar(255) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `tbl_news_category`
--

INSERT INTO `tbl_news_category` (`cid`, `category_name`, `category_image`, `status`) VALUES
(4, 'Sports', '22814_sports.jpg', 1),
(5, 'Business', '4591_download.jpg', 1),
(6, 'Bollywood', '99144_bollywood.jpg', 1),
(7, 'World', '91771_world.jpg', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_settings`
--

CREATE TABLE IF NOT EXISTS `tbl_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) NOT NULL,
  `app_logo` varchar(255) NOT NULL,
  `app_email` varchar(255) NOT NULL,
  `app_website` varchar(255) NOT NULL,
  `app_description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_settings`
--

INSERT INTO `tbl_settings` (`id`, `app_name`, `app_logo`, `app_email`, `app_website`, `app_description`) VALUES
(1, 'News Application', 'viavi_logo.png', 'info@viaviweb.com', 'http://viaviweb.in', '<p>The content provided in this app is demo are for demonstrative purposes only and are not available for use by buyers.i not owner of that all &nbsp;information we are found on Internet by google</p>\r\n');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
