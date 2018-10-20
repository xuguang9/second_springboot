package xg.inclass.second_springboot.config;

//@Configuration
//@EnableWebSecurity
//@Profile("test")
public class SecurityConfig //extends WebSecurityConfigurerAdapter
{

  /*  @Autowired
    private ReaderRepository readerRepository;

   // @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").access("hasRole('READER')")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true");
    }

    //@Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(username -> readerRepository.findOne(username);
        auth.userDetailsService(new UserDetailsService() {
                    @Override
                    public UserDetails loadUserByUsername(String username)
                            throws UsernameNotFoundException {
                        return readerRepository.findById(username).orElse(null);
                    }
                });
    }*/
}
