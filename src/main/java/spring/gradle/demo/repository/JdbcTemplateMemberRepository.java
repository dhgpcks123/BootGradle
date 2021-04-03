package spring.gradle.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import spring.gradle.demo.domain.Member;

public class JdbcTemplateMemberRepository implements MemberRepository{

	private final JdbcTemplate jdbcTemplate;
	
	//@Autowired 생성자 하나일 때 생략해도 빈 생성 해준다
	public JdbcTemplateMemberRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Member save(Member member) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", member.getName());
		
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		member.setId(key.longValue());
		return member;
		// 얘는 또 쿼리를 안 짜넹.. insert를 만들어줘서 넘기는구나.
	}

	@Override
	public Optional<Member> findById(Long id) {
		List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
		return result.stream().findAny();
	}

	@Override
	public Optional<Member> findByName(String name) {
		List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
		return result.stream().findAny();
	}

	@Override
	public List<Member> findAll() {
		return jdbcTemplate.query("select * from member", memberRowMapper());
	}
	
	//람다식.... ?
	private RowMapper<Member> memberRowMapper(){
		return (rs, rowNum) ->{
			Member member = new Member();
			member.setId(rs.getLong("id"));
			member.setName(rs.getString("name"));
			return member;
		};
	}

}
