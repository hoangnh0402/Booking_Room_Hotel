import Title from "../components/Title";
import Introduction from "../components/About-us/Introduction";
import Header from "../components/Header";

const AboutUs = () => {
  return (
    <div>
      <Header />
      {/* <Title title="Rooms" /> */}
      {Title("About Us", "About Us")}
      <Introduction />
    </div>
  );
};

export default AboutUs;
