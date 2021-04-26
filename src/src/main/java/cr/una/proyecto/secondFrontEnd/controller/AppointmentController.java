package cr.una.proyecto.secondFrontEnd.controller;

import cr.una.proyecto.secondFrontEnd.model.Appointment;
import cr.una.proyecto.secondFrontEnd.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Felipe Guzman Rodriguez
 * @author Manfred Joel Aguero Campos
 */
@Controller
public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(){
        appointmentService = new AppointmentService();
    }

    @GetMapping("/appointments")
    public String appointments(Model model) {
        List<Appointment> appointmentList;
        appointmentList = appointmentService.loadAllAppointments();
        model.addAttribute("appointmentList", appointmentList);
        return "appointments";
    }

    @PostMapping("/appointments")
    public String appointments(Model model, HttpServletRequest request) {
        List<Appointment> appointmentList;
        String filterTxt = request.getParameter("filterTxt");

        if (filterTxt != "") {
            appointmentList = appointmentService.searchAppointmentsByTerm(filterTxt);
        } else {
            appointmentList = appointmentService.loadAllAppointments();
        }
        model.addAttribute("appointmentList", appointmentList);

        return "appointments";
    }
    @GetMapping("/appointments/confirm")
    public String confirmAppointment(Model model, HttpServletRequest request) {
        Appointment updatedAppointment=appointmentService.findAppointmentById
                (Integer.parseInt(request.getParameter("id")));
        updatedAppointment.setConfirmed(!updatedAppointment.isConfirmed());
        appointmentService.updateAppointment(updatedAppointment,updatedAppointment.getId());
        List<Appointment> appointmentList;
        appointmentList = appointmentService.loadAllAppointments();
        model.addAttribute("appointmentList", appointmentList);
        return "appointments";
    }
}
