package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.constant.Additive;
import edu.hm.cs.fs.common.constant.MealType;
import edu.hm.cs.fs.common.constant.StudentWorkMunich;
import edu.hm.cs.fs.common.model.Meal;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The meal stores the data for a meal with the name of the meal and the date.
 *
 * @author Fabio
 * @version 2
 */
public class MealParser extends AbstractHtmlParser<Meal> {
    private final static Logger LOG = Logger.getLogger(MealParser.class);
    private static final Pattern ADDITIVES_PATTERN = Pattern.compile("\\(([0-9,]+)");
    private static final Pattern FOOD_PART_PATTERN = Pattern.compile("\\(([RS,]+)");
    private static final Pattern FOOD_TYPE_PATTERN = Pattern.compile("\\(([vf])");

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

        return document.getElementsByClass("menu").parallelStream()
                .map(menu -> {
                    final String dateStr = menu.getElementsByTag("strong").get(0).text();
                    Date date;
                    try {
                        date = new SimpleDateFormat("dd.MM.yyyy").parse(dateStr.substring(dateStr.indexOf(",") + 1));
                    } catch (ParseException e) {
                        LOG.warn(e);
                        date = new Date();
                    }
                    final Date finalDate = date;

                    return menu.getElementsByClass("beschreibung").parallelStream()
                            .map(description -> {
                                Meal meal = new Meal();
                                meal.setDate(finalDate);

                                if (!description.getElementsByClass("fleischlos").isEmpty()) {
                                    meal.setType(MealType.MEATLESS);
                                } else if (!description.getElementsByClass("fleisch").isEmpty()) {
                                    meal.setType(MealType.MEAT);
                                } else if (!description.getElementsByClass("vegan").isEmpty()) {
                                    meal.setType(MealType.VEGAN);
                                }

                                MealType typeRaw = meal.getType();
                                List<Additive> additives = new ArrayList<>();
                                String nameRaw = description.getElementsByAttribute("style").get(0).text();
                                nameRaw = nameRaw.replaceAll("fleischlos", "");
                                nameRaw = nameRaw.replaceAll("vegan", "");
                                nameRaw = nameRaw.replaceAll("mit Fleisch", "");

                                Matcher matcher = FOOD_PART_PATTERN.matcher(nameRaw);
                                if (matcher.find()) {
                                    String group = matcher.group(1);
                                    nameRaw = nameRaw.replaceAll("\\([RS,]+\\)", "");
                                    if (!Strings.isNullOrEmpty(group)) {
                                        if (group.contains("R")) {
                                            additives.add(Additive.BEEF);
                                        }
                                        if (group.contains("S")) {
                                            additives.add(Additive.PIG);
                                        }
                                    }
                                }

                                matcher = ADDITIVES_PATTERN.matcher(nameRaw);
                                if (matcher.find()) {
                                    final String group = matcher.group(1);
                                    nameRaw = nameRaw.replaceAll("\\([0-9,]+\\)", "");
                                    if (!Strings.isNullOrEmpty(group)) {
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
                                if (matcher.find()) {
                                    final String group = matcher.group(1);
                                    nameRaw = nameRaw.replaceAll("\\([vf]\\)", "");
                                    if (!Strings.isNullOrEmpty(group)) {
                                        if (group.contains("v")) {
                                            typeRaw = MealType.VEGAN;
                                        }
                                        if (group.contains("f")) {
                                            typeRaw = MealType.MEATLESS;
                                        }
                                    }
                                }

                                meal.setName(nameRaw);
                                meal.setType(typeRaw);
                                meal.setAdditives(additives);
                                return meal;
                            })
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }
}
