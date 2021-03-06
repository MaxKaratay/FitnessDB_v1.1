package com.fitdb.service;

import com.fitdb.domain.Day;
import com.fitdb.domain.Schedule;
import com.fitdb.repository.DayRepository;
import com.fitdb.repository.PeriodRepository;
import com.fitdb.repository.TimeRepository;
import com.fitdb.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PeriodRepository periodRepository;
    @Autowired
    DayRepository dayRepository;
    @Autowired
    TimeRepository timeRepository;
    @Autowired
    InstructService instructService;

    public Map<Day, Iterable<Schedule>> scheduleByDay() {
        Iterable<Day> days = dayRepository.findAll();
        Map<Day, Iterable<Schedule>> result = new TreeMap<>(Comparator.comparingLong(Day::getID));
        for (Day d : days) {
            result.put(d, scheduleRepository.findAllByDay(d));
        }
        return result;
    }

    public void loadResources(Model model) {
        model.addAttribute("instructors", instructService.getInstructListByDiscipline());
        model.addAttribute("disciplines",  instructService.getDisciplineRepository().findAll());
        model.addAttribute("times", timeRepository.findAll());
    }

    public ScheduleRepository getScheduleRepository() {
        return scheduleRepository;
    }

    public PeriodRepository getPeriodRepository() {
        return periodRepository;
    }

    public DayRepository getDayRepository() {
        return dayRepository;
    }

    public TimeRepository getTimeRepository() {
        return timeRepository;
    }

    public InstructService getInstructService() {
        return instructService;
    }
}
