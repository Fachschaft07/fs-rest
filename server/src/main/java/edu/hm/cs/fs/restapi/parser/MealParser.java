package edu.hm.cs.fs.restapi.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.constant.Additive;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.hm.cs.fs.common.constant.MealType;
import edu.hm.cs.fs.common.constant.StudentWorkMunich;
import edu.hm.cs.fs.common.model.Meal;

/**
 * The meal stores the data for a meal with the name of the meal and the date.
 *
 * @author Fabio
 * @version 2
 */
public class MealParser extends AbstractHtmlParser<Meal> {
    private static final Pattern ADDITIVES_PATTERN = Pattern.compile("\\(([0-9,]+)");
    private static final Pattern FOOD_PART_PATTERN = Pattern.compile("\\(([RS,]+)");
    private static final Pattern FOOD_TYPE_PATTERN = Pattern.compile("\\(([vf])");
    private static final DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public MealParser(StudentWorkMunich studentWorkMunich) {
        super(studentWorkMunich.getUrl());
    }

    @Override
    public List<Meal> readDoc(final Document document) {
        /*
        <table class="menu">
            <tr>
                <td class="headline"></td>
                <td class="headline">
                    <span style="float:left">
                        <a class="heute_2015-03-16 anker" name="heute"><strong>Montag, 16.03.2015</strong></a>
                        (<a href="mensa/speiseplan/speiseplan_2015-03-16_431_-de.html">Beilagen</a>)
                        <!--
                                <a href="mensa/speiseplan/speiseplan_2015-03-16_431_-de.pdf">PDF</a>
                        -->
                    </span>
                    <span style="float:right">
                        (Wochenplan als <a href="mensa/speiseplan/speiseplan-kw_2015-12_431_-de.pdf">PDF</a>)
                    </span>
                </td>
            </tr>

            <tr>
                <td class="gericht"><span class="stwm-artname">Tagesgericht 1</span></td>
                <td class="beschreibung" style="padding-right:30px">
                    <span style="float:left">Farfalle mit Champignonrahmsauce (f)</span>
                    <span title="fleischloses Gericht" class="fleischlos"><span>&nbsp;</span>fleischlos</span>


                </td>
            </tr>
            ...
         */

        List<Meal> result = new ArrayList<>();

        final Elements menuList = document.getElementsByClass("menu");
        for (int index = 0; index < menuList.size(); index++) {
            final Element menu = menuList.get(index);

            final String dateStr = menu.getElementsByTag("strong").get(0).text();
            Date date;
            try {
                date = sdf.parse(dateStr.substring(dateStr.indexOf(",")+1));
            } catch (ParseException e) {
                date = new Date();
            }

            final Elements descriptionList = menu.getElementsByClass("beschreibung");
            for (int indexDesc = 0; indexDesc < descriptionList.size(); indexDesc++) {
                Meal meal = new Meal();
                meal.setDate(date);

                final Element description = descriptionList.get(indexDesc);

                if(!description.getElementsByClass("fleischlos").isEmpty()) {
                    meal.setType(MealType.MEATLESS);
                } else if(!description.getElementsByClass("fleisch").isEmpty()) {
                    meal.setType(MealType.MEAT);
                } else if(!description.getElementsByClass("vegan").isEmpty()) {
                    meal.setType(MealType.VEGAN);
                }

                MealType typeRaw = meal.getType();
                List<Additive> additives = new ArrayList<>();
                String nameRaw = description.getElementsByAttribute("style").get(0).text();
                // 2015-08-19: BugFix: Fixed the removement of the meal type of the end of the name
                StringBuilder nameBuilder = new StringBuilder(nameRaw);
                if(nameRaw.contains("fleischlos")) {
                    nameBuilder.replace(nameBuilder.lastIndexOf("fleischlos"), nameBuilder.length(), "");
                } else if(nameRaw.contains("vegan")) {
                    nameBuilder.replace(nameBuilder.lastIndexOf("vegan"), nameBuilder.length(), "");
                } else if(nameRaw.contains("mit Fleisch")) {
                    nameBuilder.replace(nameBuilder.lastIndexOf("mit Fleisch"), nameBuilder.length(), "");
                }
                nameBuilder.trimToSize();
                nameRaw = nameBuilder.toString();
                //nameRaw = nameRaw.replaceFirst("\\sfleischlos", "");
                //nameRaw = nameRaw.replaceFirst("\\svegan", "");
                //nameRaw = nameRaw.replaceFirst("\\smit Fleisch", "");

                Matcher matcher = FOOD_PART_PATTERN.matcher(nameRaw);
                if(matcher.find()) {
                    String group = matcher.group(1);
                    nameRaw = nameRaw.replaceAll("\\([RS,]+\\)", "");
                    if(!Strings.isNullOrEmpty(group)) {
                        if(group.contains("R")) {
                            additives.add(Additive.BEEF);
                        }
                        if(group.contains("S")) {
                            additives.add(Additive.PIG);
                        }
                    }
                }

                matcher = ADDITIVES_PATTERN.matcher(nameRaw);
                if(matcher.find()) {
                    final String group = matcher.group(1);
                    nameRaw = nameRaw.replaceAll("\\([0-9,]+\\)", "");
                    if(!Strings.isNullOrEmpty(group)) {
                        final String additiveStr = group.trim();
                        final List<String> strings = Arrays.asList(additiveStr.split(","));
                        for (String string : strings) {
                            Additive additive = Additive.of(string);
                            if (additive != null) {
                                additives.add(additive);
                            }
                        }
                    }
                }

                matcher = FOOD_TYPE_PATTERN.matcher(nameRaw);
                if(matcher.find()) {
                    final String group = matcher.group(1);
                    nameRaw = nameRaw.replaceAll("\\([vf]\\)", "");
                    if(!Strings.isNullOrEmpty(group)) {
                        if(group.contains("v")) {
                            typeRaw = MealType.VEGAN;
                        }
                        if(group.contains("f")) {
                            typeRaw = MealType.MEATLESS;
                        }
                    }
                }

                meal.setName(nameRaw);
                meal.setType(typeRaw);
                meal.setAdditives(additives);

                result.add(meal);
            }
        }

        return result;
    }
}
