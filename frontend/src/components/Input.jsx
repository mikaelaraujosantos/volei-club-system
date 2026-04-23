function Input({
  type,
  placeholder,
  value,
  onChange,
  erro
}) {

  return (

    <input
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      className={`input ${erro ? "input-erro" : ""}`}
    />

  )

}

export default Input