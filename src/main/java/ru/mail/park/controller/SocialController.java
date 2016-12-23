///* package ru.mail.park.controller;
//
//import org.springframework.social.vkontakte.api.VKontakte;
//import org.springframework.social.vkontakte.api.VKontakteProfile;
//import org.springframework.social.vkontakte.api.impl.json.VKArray;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.inject.Inject;
//
//@Controller
//public class SocialController {
//
//    private final VKontakte vKontakte;
//
//    @Inject
//    public SocialController(VKontakte vKontakte) {
//        this.vKontakte = vKontakte;
//    }
//
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String home(Model model) {
//        VKArray<VKontakteProfile> friends = vKontakte.friendsOperations().get();
//
//        model.addAttribute("friends", friends);
//        return "home";
//    }
//
//} */
